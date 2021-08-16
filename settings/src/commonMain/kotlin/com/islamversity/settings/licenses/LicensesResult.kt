package com.islamversity.settings.licenses

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviResult
import com.islamversity.settings.models.LicensesUIModel

sealed class LicensesResult : MviResult {
    object LastStable : LicensesResult()
    class Error(val err: BaseState.ErrorState) : LicensesResult()

    data class LicensesList(val list: List<LicensesUIModel>) : LicensesResult()
}