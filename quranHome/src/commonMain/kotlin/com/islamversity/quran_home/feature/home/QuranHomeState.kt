package com.islamversity.quran_home.feature.home

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState

data class QuranHomeState(
    override val base: BaseState
) : BaseViewState {
    companion object {
        fun idle() =
            QuranHomeState(base = BaseState.stable())
    }
}