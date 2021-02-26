package com.islamversity.quran_home.feature.home

import com.islamversity.core.FlowBlock
import com.islamversity.core.Mapper
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.core.ofType
import com.islamversity.domain.model.surah.SurahStateRepoModel
import com.islamversity.domain.repo.surah.GetSurahStateUsecase
import com.islamversity.navigation.Navigator
import com.islamversity.navigation.Screens
import com.islamversity.navigation.model.SearchLocalModel
import com.islamversity.navigation.model.SurahLocalModel
import com.islamversity.navigation.navigateTo
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map

class QuranHomeProcessor(
    navigator: Navigator,
    getSurahStateUsecase: GetSurahStateUsecase,
    surahStateMapper: Mapper<SurahStateRepoModel?, SavedSurahState?>,
) : BaseProcessor<QuranHomeIntent, QuranHomeResult>() {
    override fun transformers(): List<FlowBlock<QuranHomeIntent, QuranHomeResult>> =
        listOf(loadLastVisitState, lastVisitClick, settingsClicked, searchClicked)

    private val loadLastVisitState: FlowBlock<QuranHomeIntent, QuranHomeResult> = {
        ofType<QuranHomeIntent.Initial>()
            .flatMapMerge {
                getSurahStateUsecase.getState()
            }
            .map {state->
                QuranHomeResult.Success(savedSurahState = surahStateMapper.map(state))
            }
    }

    private val searchClicked: FlowBlock<QuranHomeIntent, QuranHomeResult> = {
        ofType<QuranHomeIntent.SearchClicked>()
            .map {
                //TODO ADD TRANSITION
                Screens.Search(SearchLocalModel())
            }
            .navigateTo(navigator)
    }

    private val settingsClicked: FlowBlock<QuranHomeIntent, QuranHomeResult> = {
        ofType<QuranHomeIntent.SettingsClicked>()
            .map {
                Screens.Settings()
            }
            .navigateTo(navigator)
    }

    private val lastVisitClick: FlowBlock<QuranHomeIntent, QuranHomeResult> = {
        ofType<QuranHomeIntent.LastVisitClicked>()
            .map { it.state }
            .map { SurahLocalModel.FullSurah(it.surahName,it.surahID,it.startingAyaOrder) }
            .map { Screens.Surah(it) }
            .navigateTo(navigator)
    }
}