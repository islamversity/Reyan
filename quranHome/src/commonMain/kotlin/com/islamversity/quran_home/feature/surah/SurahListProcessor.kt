package com.islamversity.quran_home.feature.surah

import com.islamversity.core.FlowBlock
import com.islamversity.core.Mapper
import com.islamversity.core.listMap
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.core.ofType
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.repo.surah.GetSurahUsecase
import com.islamversity.navigation.Navigator
import com.islamversity.navigation.Screens
import com.islamversity.navigation.model.SearchLocalModel
import com.islamversity.navigation.model.SurahLocalModel
import com.islamversity.navigation.navigateTo
import com.islamversity.quran_home.feature.surah.model.SurahUIModel
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map

class SurahListProcessor(
    private val navigator: Navigator,
    private val surahUsecase: GetSurahUsecase,
    private val surahMapper: Mapper<SurahRepoModel, SurahUIModel>
) : BaseProcessor<SurahListIntent, SurahListResult>() {
    override fun transformers(): List<FlowBlock<SurahListIntent, SurahListResult>> =
        listOf(loadSurahs,itemClick)

    private val loadSurahs: FlowBlock<SurahListIntent, SurahListResult> = {
        ofType<SurahListIntent.Initial>()
            .flatMapMerge {
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
            .map {
                Screens.Surah(SurahLocalModel(it.id.id, it.mainName, 0))
            }
            .navigateTo(navigator)
    }
}