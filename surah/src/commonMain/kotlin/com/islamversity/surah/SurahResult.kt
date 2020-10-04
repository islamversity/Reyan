package com.islamversity.surah

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviResult
import com.islamversity.surah.model.UIItem

sealed class SurahResult : MviResult {
    object LastStable : SurahResult()
    object Loading : SurahResult()

    data class Error(val err: BaseState.ErrorState) : SurahResult()

    data class Items(
        val items: List<UIItem>
    ) : SurahResult()

    data class ShowAyaNumber(
        val position: Int,
        val id: String,
        val orderID: Long,
    ) : SurahResult()
}