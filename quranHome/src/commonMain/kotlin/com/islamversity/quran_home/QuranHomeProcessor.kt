package com.islamversity.quran_home

import com.islamversity.core.*
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.domain.model.JuzRepoModel
import com.islamversity.domain.repo.juz.JuzListRepo
import com.islamversity.domain.repo.surah.GetSurahsUsecase
import com.islamversity.navigation.Navigator
import com.islamversity.navigation.Screens
import com.islamversity.navigation.model.SearchLocalModel
import com.islamversity.quran_home.model.JozUIModel
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.navigation.navigateTo
import com.islamversity.quran_home.model.SurahUIModel
import kotlinx.coroutines.flow.*

class QuranHomeProcessor(
    private val navigator: Navigator,
    private val surahsUsecase: GetSurahsUsecase,
    private val juzListRepo: JuzListRepo,
    private val surahMapper: Mapper<SurahRepoModel, SurahUIModel>,
    private val juzMapper: Mapper<JuzRepoModel, JozUIModel>
) : BaseProcessor<QuranHomeIntent, QuranHomeResult>() {
    override fun transformers(): List<FlowBlock<QuranHomeIntent, QuranHomeResult>> =
        listOf(loadSurahs, loadJoz, itemClick, searchClick)

    private val loadSurahs: FlowBlock<QuranHomeIntent, QuranHomeResult> = {
        ofType<QuranHomeIntent.Initial>()
            .flatMapMerge {
                surahsUsecase.getSurahs()
            }
            .map {
                QuranHomeResult.SurahsSuccess(surahMapper.listMap(it))
            }
    }

    private val loadJoz: FlowBlock<QuranHomeIntent, QuranHomeResult> = {
        ofType<QuranHomeIntent.Initial>()
            .flatMapMerge {
                juzListRepo.getAllJuz()
            }
            .map {
                QuranHomeResult.JuzSuccess(juzMapper.listMap(it))
            }
    }

    private val itemClick: FlowBlock<QuranHomeIntent, QuranHomeResult> = {
        ofType<QuranHomeIntent.ItemClick>()
            .map {
                it.action.surah
            }
            .map {
                //TODO surah details
                Screens.Search(SearchLocalModel())
            }
            .navigateTo(navigator)
    }

    private val searchClick :  FlowBlock<QuranHomeIntent, QuranHomeResult> = {
        ofType<QuranHomeIntent.SearchClicked>()
            .map {
                Screens.Search(SearchLocalModel())
            }
            .navigateTo(navigator)
    }
}