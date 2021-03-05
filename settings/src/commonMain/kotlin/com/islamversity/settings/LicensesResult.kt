package com.islamversity.settings

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviResult

sealed class LicensesResult : MviResult {
    object LastStable : LicensesResult()
    class Error(val err: BaseState.ErrorState) : LicensesResult()
}