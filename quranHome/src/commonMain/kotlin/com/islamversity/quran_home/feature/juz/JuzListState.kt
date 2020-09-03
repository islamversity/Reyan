package com.islamversity.quran_home.feature.juz

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState
import com.islamversity.quran_home.feature.juz.model.JozUIModel

data class JuzListState(
    override val base: BaseState,
    val juzList: List<JozUIModel>
) : BaseViewState {
    companion object {
        fun idle() =
            JuzListState(
                base = BaseState.stable(),
                juzList = emptyList()
            )
    }
}