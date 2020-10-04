package com.islamversity.quran_home.feature.juz

import com.islamversity.core.FlowBlock
import com.islamversity.core.Mapper
import com.islamversity.core.listMap
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.core.ofType
import com.islamversity.domain.model.JuzRepoModel
import com.islamversity.domain.repo.juz.JuzListRepo
import com.islamversity.domain.repo.juz.JuzListUsecase
import com.islamversity.navigation.Navigator
import com.islamversity.navigation.Screens
import com.islamversity.navigation.model.SearchLocalModel
import com.islamversity.navigation.model.SurahLocalModel
import com.islamversity.navigation.navigateTo
import com.islamversity.quran_home.feature.juz.model.JozUIModel
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map

class JuzListProcessor(
    private val navigator: Navigator,
    private val juzListUsecase: JuzListUsecase,
    private val juzMapper: Mapper<JuzRepoModel, JozUIModel>
) : BaseProcessor<JuzListIntent, JuzListResult>() {
    override fun transformers(): List<FlowBlock<JuzListIntent, JuzListResult>> =
        listOf(loadJoz, itemClick)


    private val loadJoz: FlowBlock<JuzListIntent, JuzListResult> = {
        ofType<JuzListIntent.Initial>()
            .flatMapMerge {
                juzListUsecase.getAllJuz()
            }
            .map {
                JuzListResult.JuzSuccess(
                    juzMapper.listMap(
                        it
                    )
                )
            }
    }

    private val itemClick: FlowBlock<JuzListIntent, JuzListResult> = {
        ofType<JuzListIntent.ItemClick>()
            .map {
                it.action.juz
            }
            .map {
                Screens.Surah(SurahLocalModel(
                    it.startingSurahId,
                    it.startingSurahName,
                    it.startingAyaOrder,
                ))
            }
            .navigateTo(navigator)
    }
}