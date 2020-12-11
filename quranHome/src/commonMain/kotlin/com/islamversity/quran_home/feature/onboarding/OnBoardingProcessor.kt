package com.islamversity.quran_home.feature.onboarding

import com.islamversity.core.*
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.domain.model.FillingStatus
import com.islamversity.domain.model.JuzRepoModel
import com.islamversity.domain.repo.juz.JuzListRepo
import com.islamversity.domain.repo.juz.JuzListUsecase
import com.islamversity.domain.usecase.DatabaseFillerUseCase
import com.islamversity.navigation.Navigator
import com.islamversity.navigation.Screens
import com.islamversity.navigation.model.SearchLocalModel
import com.islamversity.navigation.model.SurahLocalModel
import com.islamversity.navigation.navigateTo
import com.islamversity.quran_home.feature.juz.model.JozUIModel
import kotlinx.coroutines.flow.*

class OnBoardingProcessor(
    private val navigator: Navigator,
    private val fillerUseCase: DatabaseFillerUseCase
) : BaseProcessor<OnBoardingIntent, OnBoardingResult>() {
    override fun transformers(): List<FlowBlock<OnBoardingIntent, OnBoardingResult>> =
        listOf(startFilling, continueProcessor)

    private val startFilling: FlowBlock<OnBoardingIntent, OnBoardingResult> = {
        ofType<OnBoardingIntent.Initial>()
            .filter {
                fillerUseCase.status().first() == FillingStatus.Idle
            }
            .publish(
                {
                    startFilling<OnBoardingIntent, OnBoardingResult>()
                },
                {
                    flatMapLatest {
                        listenToUpdates()
//                        fillingSimulator()
                    }
                })
    }

    private fun listenToUpdates(): Flow<OnBoardingResult> =
        fillerUseCase.status()
            .filter {
                it is FillingStatus.Filling || it is FillingStatus.Done
            }
            .map {
                when (it) {
                    is FillingStatus.Filling ->
                        OnBoardingResult.Loading(it.percent)
                    FillingStatus.Done -> OnBoardingResult.InitializingDone
                    else -> error("we are filtering for these two types only")
                }
            }


    private fun fillingSimulator() = flow {
        repeat(5){
            emit(OnBoardingResult.Loading(it  * 20))
            kotlinx.coroutines.delay(1000)
        }

        emit(OnBoardingResult.InitializingDone)
    }

    private fun <T, R> Flow<T>.startFilling(): Flow<R> =
        transform {
            fillerUseCase.fill()
        }


    private val continueProcessor: FlowBlock<OnBoardingIntent, OnBoardingResult> = {
        ofType<OnBoardingIntent.Continue>()
            .map {
                Screens.Home()
            }
            .navigateTo(navigator)
    }
}