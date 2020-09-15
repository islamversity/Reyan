package com.islamversity.surah

import com.islamversity.core.*
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.domain.model.aya.AyaRepoModel
import com.islamversity.domain.model.surah.BismillahRepoType
import com.islamversity.domain.model.surah.SurahID
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.domain.repo.aya.GetAyaUseCase
import com.islamversity.domain.repo.bismillah.BismillahUsecase
import com.islamversity.domain.repo.surah.GetSurahUsecase
import com.islamversity.navigation.Navigator
import com.islamversity.navigation.model.BismillahType
import com.islamversity.surah.model.AyaUIModel
import kotlinx.coroutines.flow.*

class SurahProcessor(
    private val navigator: Navigator,
    private val getAyaUseCase: GetAyaUseCase,
    private val ayaMapper: Mapper<AyaRepoModel, AyaUIModel>,
    private val settingRepo: SettingRepo,
    private val surahUsecase: GetSurahUsecase,
    private val bismillahUsecase: BismillahUsecase
) : BaseProcessor<SurahIntent, SurahResult>() {

    override fun transformers(): List<FlowBlock<SurahIntent, SurahResult>> = listOf(
        processInitialFetch
    )

    private val processInitialFetch: FlowBlock<SurahIntent, SurahResult> = {
        ofType<SurahIntent.Initial>()
            .flatMapMerge {
                surahUsecase.getSurah(SurahID(it.surahId))
            }
            .publish(
                listOf(
                    {
                        filter { it == null }.map {
                            SurahResult.SurahNotFound
                        }
                    },
                    {
                        filterNotNull().let(getMainAyaProcessor)
                    },
                    {
                        filterNotNull().let(bismillahTypeProcessor)
                    }
                )
            )
    }

    private val getMainAyaProcessor: FlowBlock<SurahRepoModel, SurahResult> = {
        flatMapMerge {
            getAyaUseCase.observeAyaMain(it.id)
                .mapListWith(ayaMapper)
                .combine(settingRepo.getCurrentFontSize().map { it.size.toInt() }) { uiModel, font ->
                    uiModel.map {
                        it.copy(fontSize = font)
                    } to font
                }
                .map { ayas ->
                    SurahResult.MainAyasLoaded(
                        ayas.first,
                        ayas.second,
                    )
                }
        }
    }

    private val bismillahTypeProcessor: FlowBlock<SurahRepoModel, SurahResult> = {
        publish(
            {
                map {
                    when (it.bismillahType) {
                        BismillahRepoType.NEEDED -> SurahResult.Bismillah.Show
                        BismillahRepoType.FIRST_AYA,
                        BismillahRepoType.NONE -> SurahResult.Bismillah.Hide
                    }
                }
            },
            {
                filter { it.bismillahType == BismillahRepoType.NEEDED }
                    .flatMapMerge {
                        bismillahUsecase.getBismillahWithType(it.bismillahType)
                            .filterNotNull()
                            .map {
                                SurahResult.Bismillah.Content(it.content)
                            }
                    }
            }
        )
    }
}