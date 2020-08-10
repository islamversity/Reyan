package com.islamversity.search

import com.islamversity.core.mvi.BasePresenter
import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviProcessor

class SearchPresenter(
    processor: MviProcessor<SearchIntent, SearchResult>
) : BasePresenter<SearchIntent, SearchState, SearchResult>(
    processor,
    SearchState.idle()
) {

    override fun reduce(preState: SearchState, result: SearchResult): SearchState =
        when (result) {
            SearchResult.LastStable ->
                preState.copy(
                    base = BaseState.stable()
                )
            is SearchResult.Error ->
                preState.copy(
                    base = BaseState.withError(result.err)
                )

            SearchResult.Loading -> preState.copy(
                    base = BaseState.loading()
                    )
            is SearchResult.Data -> preState.copy(
                base = BaseState.stable(),
                items = result.items
            )
        }
}