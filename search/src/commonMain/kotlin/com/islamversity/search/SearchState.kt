package com.islamversity.search

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState
import com.islamversity.search.model.SurahUIModel

data class SearchState(
    override val base: BaseState,
    val items : List<SurahUIModel> = emptyList()
) : BaseViewState {
    companion object {
        fun idle() =
            SearchState(
                base = BaseState.stable()
            )
    }
}