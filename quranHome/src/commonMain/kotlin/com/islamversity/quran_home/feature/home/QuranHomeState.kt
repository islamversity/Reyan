package com.islamversity.quran_home.feature.home

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState

data class QuranHomeState(
    override val base: BaseState,
    val savedSurahState: SavedSurahState? = null,
    val tabPosition: Int
) : BaseViewState {
    companion object {
        fun idle() =
            QuranHomeState(
                base = BaseState.stable(),
                savedSurahState = null,
                tabPosition = 0
            )
    }
}

data class SavedSurahState(
    val surahName: String,
    val surahID: String,
    val startingAyaOrder: Long,
)