package com.islamversity.surah

import com.islamversity.core.*
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.domain.model.aya.AyaRepoModel
import com.islamversity.domain.model.surah.SurahID
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.domain.repo.aya.GetAyaUseCase
import com.islamversity.navigation.Navigator
import com.islamversity.navigation.model.BismillahType
import com.islamversity.surah.model.AyaUIModel
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map

class SurahProcessor(
    private val navigator: Navigator,
    private val getAyaUseCase: GetAyaUseCase,
    private val ayaMapper: Mapper<AyaRepoModel, AyaUIModel>,
    private val settingRepo: SettingRepo,
) : BaseProcessor<SurahIntent, SurahResult>() {

    override fun transformers(): List<FlowBlock<SurahIntent, SurahResult>> = listOf(
        getMainAyaProcessor,
        bismillahTypeProcessor,
    )

    private val getMainAyaProcessor: FlowBlock<SurahIntent, SurahResult> = {
        ofType<SurahIntent.Initial>()
            .flatMapMerge {
                getAyaUseCase.observeAyaMain(SurahID(it.surahId))
                    .mapListWith(ayaMapper)
                    .map {
                        val fontSize = settingRepo.getCurrentFontSize()
                        it.map {
                            it.copy(fontSize = fontSize.size.toInt())
                        } to fontSize
                    }
                    .map { ayas ->
                        SurahResult.MainAyasLoaded(ayas.first, it.startingAyaPosition, ayas.second.size.toInt())
                    }
            }
    }

    private val bismillahTypeProcessor: FlowBlock<SurahIntent, SurahResult> = {
        ofType<SurahIntent.Initial>()
            .publish({
                map {
                    when (it.bismillahType) {
                        BismillahType.NEEDED -> SurahResult.Bismillah.Show
                        BismillahType.FIRST_AYA,
                        BismillahType.NONE -> SurahResult.Bismillah.Hide
                    }
                }
            }, {
                TODO("process bismillah and get from the database if needed")
            })
            .map {
                SurahResult.LastStable
            }

    }
}