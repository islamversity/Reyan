package com.islamversity.surah

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState
import com.islamversity.surah.model.UIItem

data class SurahState(
    override val base: BaseState,
    val showBismillah : Boolean,
    val bismillah : String,
    val items : List<UIItem>,
    val startFrom : Int,
    val closeScreen : Boolean,
) : BaseViewState {
    companion object {
        fun idle() =
            SurahState(
                base = BaseState.stable(),
                items = emptyList(),
                startFrom = 0,
                showBismillah = false,
                bismillah = "",
                closeScreen = false
            )
    }
}