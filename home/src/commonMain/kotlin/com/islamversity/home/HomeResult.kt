package com.islamversity.home

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviResult

sealed class HomeResult : MviResult {
    object Loading : HomeResult()
    object LastStable : HomeResult()
    class Error(val err : BaseState.ErrorState) : HomeResult()
}