package com.islamversity.quran_home.feature.surah

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState
import com.islamversity.quran_home.feature.surah.model.SurahUIModel

data class SurahListState(
    override val base: BaseState,
    val surahList: List<SurahUIModel>
) : BaseViewState {
    companion object {
        fun idle() =
            SurahListState(
                base = BaseState.stable(),
                surahList = emptyList()
            )
    }
}