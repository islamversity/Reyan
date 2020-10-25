package com.islamversity.search

import com.islamversity.core.FlowBlock
import com.islamversity.core.Mapper
import com.islamversity.core.listMap
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.core.ofType
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.repo.surah.SearchSurahNameUseCase
import com.islamversity.navigation.NavigationAnimation
import com.islamversity.navigation.Navigator
import com.islamversity.navigation.Screens
import com.islamversity.navigation.model.SurahLocalModel
import com.islamversity.navigation.navigateTo
import com.islamversity.search.model.SurahUIModel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class SearchProcessor(
    searchUsecase : SearchSurahNameUseCase,
    navigator: Navigator,
    mapper: Mapper<SurahRepoModel, SurahUIModel>
) : BaseProcessor<SearchIntent, SearchResult>() {

    override fun transformers(): List<FlowBlock<SearchIntent, SearchResult>> = listOf(
        newSearch,
        itemClick
    )

    private val newSearch: FlowBlock<SearchIntent, SearchResult> = {
        ofType<SearchIntent.Search>()
            .flatMapLatest {
                searchUsecase.search(it.query)
                    .map { repoItems ->
                        SearchResult.Data(mapper.listMap(repoItems))
                    }
            }
    }

    private val itemClick: FlowBlock<SearchIntent, SearchResult> = {
        ofType<SearchIntent.ItemClick>()
            .map {
                it.action.selectedItem
            }
            .map {
                SurahLocalModel(
                    surahID = it.id.id,
                    surahName = it.arabicName,
                    startingAyaOrder = 0,
                )
            }
            .map {
                Screens.Surah(
                    it,
                    NavigationAnimation.ArcFadeMove(
                        it.surahName
                    ),
                    NavigationAnimation.ArcFadeMove(
                        it.surahName
                    )
                )
            }
            .navigateTo(navigator)
    }
}