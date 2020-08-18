package com.islamversity.quran_home

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState
import com.islamversity.quran_home.model.JozUIModel
import com.islamversity.quran_home.model.SurahUIModel

data class QuranHomeState(
    override val base: BaseState,
    val surahList: List<SurahUIModel>,
    val juzList: List<JozUIModel>
) : BaseViewState {
    companion object {
        fun idle() =
            QuranHomeState(base = BaseState.stable(), surahList = emptyList(), juzList = emptyList())
    }
}