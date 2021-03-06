package com.islamversity.quran_home.feature.surah

import com.islamversity.core.*
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.repo.surah.GetSurahUsecase
import com.islamversity.navigation.Navigator
import com.islamversity.navigation.Screens
import com.islamversity.navigation.model.SurahLocalModel
import com.islamversity.navigation.navigateTo
import com.islamversity.quran_home.feature.surah.model.SurahUIModel
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class SurahListProcessor(
    private val navigator: Navigator,
    private val surahUsecase: GetSurahUsecase,
    private val surahMapper: Mapper<SurahRepoModel, SurahUIModel>
) : BaseProcessor<SurahListIntent, SurahListResult>(true) {
    override fun transformers(): List<FlowBlock<SurahListIntent, SurahListResult>> =
        listOf(loadSurahs,itemClick)

    private val loadSurahs: FlowBlock<SurahListIntent, SurahListResult> = {
        ofType<SurahListIntent.Initial>()
            .flatMapMerge {
                Logger.log {
                    "GetSurah= $it"
                }
                surahUsecase.getSurahs()

            }
            .map {
                SurahListResult.SurahsSuccess(
                    surahMapper.listMap(it)
                )
            }
    }

    private val itemClick: FlowBlock<SurahListIntent, SurahListResult> = {
        ofType<SurahListIntent.ItemClick>()
            .map {
                it.action.surah
            }
            .onEach {
                Logger.log {
                    "opening surah for id= ${it.id.id}"
                }
            }
            .map {
                Screens.Surah(SurahLocalModel.FullSurah(
                    surahName = it.mainName,
                    surahID = it.id.id,
                ))
            }
            .navigateTo(navigator)
    }
}