package com.islamversity.settings

import com.islamversity.core.mvi.MviIntent

sealed class LicensesIntent : MviIntent {
    object Initial : LicensesIntent()
}