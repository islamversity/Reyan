package com.islamversity.search

import com.islamversity.core.FlowBlock
import com.islamversity.core.Mapper
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.core.ofType
import com.islamversity.domain.model.sora.SoraRepoModel
import com.islamversity.domain.model.sora.SoraUIModel
import com.islamversity.domain.repo.SoraSearchRepo
import com.islamversity.navigation.NavigationAnimation
import com.islamversity.navigation.Navigator
import com.islamversity.navigation.Screens
import com.islamversity.navigation.model.SoraDetailLocalModel
import com.islamversity.navigation.navigateTo
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class SearchProcessor(
    repo: SoraSearchRepo,
    mapper: Mapper<SoraRepoModel, SoraUIModel>,
    private val navigator: Navigator
) : BaseProcessor<SearchIntent, SearchResult>() {

    override fun transformers(): List<FlowBlock<SearchIntent, SearchResult>> = listOf(
        newSearch,
        itemClick
    )

    private val newSearch: FlowBlock<SearchIntent, SearchResult> = {
        ofType<SearchIntent.Search>()
            .flatMapLatest {
                repo.search(it.query)
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
            .map{
                SoraDetailLocalModel(
                    backTransitionName = it.name,
                    textTransitionName = it.name,
                    soraID = it.id,
                    soraName = it.name,
                    ayaNumber = 1
                )
            }
            .map {
                Screens.SoraDetail(
                    it,
                    NavigationAnimation.ArcFadeMove(
                        it.soraName
                    ),
                    NavigationAnimation.ArcFadeMove(
                        it.soraName
                    )
                )
            }
            .navigateTo(navigator)
    }
}