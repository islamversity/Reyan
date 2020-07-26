package com.islamversity.search

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState

data class SearchState(
    override val base: BaseState
) : BaseViewState {
    companion object {
        fun idle() =
            SearchState(
                base = BaseState.stable()
            )
    }
}