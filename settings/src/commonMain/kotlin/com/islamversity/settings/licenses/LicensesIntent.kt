package com.islamversity.settings.licenses

import com.islamversity.core.mvi.MviIntent

sealed class LicensesIntent : MviIntent {
    object Initial : LicensesIntent()
}