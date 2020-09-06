package com.islamversity.surah

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState

data class SurahState(
    override val base: BaseState,
) : BaseViewState {
    companion object {
        fun idle() =
            SurahState(
                base = BaseState.stable()
            )
    }
}