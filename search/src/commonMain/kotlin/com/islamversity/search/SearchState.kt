package com.islamversity.search

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState
import com.islamversity.domain.model.sora.SoraUIModel

data class SearchState(
    override val base: BaseState,
    val items : List<SoraUIModel>? = null
) : BaseViewState {
    companion object {
        fun idle() =
            SearchState(
                base = BaseState.stable()
            )
    }
}