package com.islamversity.surah

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviResult

sealed class SurahResult : MviResult {
    object LastStable : SurahResult()
    object Loading : SurahResult()
    data class Error(val err: BaseState.ErrorState) : SurahResult()
}