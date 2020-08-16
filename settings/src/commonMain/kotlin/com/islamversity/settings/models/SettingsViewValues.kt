package com.islamversity.settings.models

import com.islamversity.domain.model.Calligraphy

data class SettingsViewValues(
    val quranReadCalligraphy: Calligraphy? = null,
    val secondaryCalligraphy: Calligraphy? = null,
    val quranReadFontSize:Double? = null) {
}