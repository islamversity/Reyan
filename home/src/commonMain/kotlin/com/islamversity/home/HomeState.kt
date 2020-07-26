package com.islamversity.home

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState

data class HomeState(
    override val base: BaseState
) : BaseViewState {
    companion object {
        fun start() = HomeState(
            base = BaseState.stable()
        )
    }
}