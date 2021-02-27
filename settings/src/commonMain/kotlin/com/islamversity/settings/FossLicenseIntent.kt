package com.islamversity.settings

import com.islamversity.core.mvi.MviIntent

sealed class FossLicenseIntent : MviIntent {
    object Initial : FossLicenseIntent()
}