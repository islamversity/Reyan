package com.islamversity.settings

import com.islamversity.core.mvi.MviIntent
import com.islamversity.settings.models.CalligraphyUIModel

sealed class SettingsIntent : MviIntent {
    object Initial : SettingsIntent()
    data class NewSurahNameCalligraphySelected(
        val calligraphy : CalligraphyUIModel
    ) : SettingsIntent()

    class NewAyaCalligraphy(
        val language: CalligraphyUIModel
    ) : SettingsIntent()

    class ChangeQuranFontSize(
        val double: Int
    ) : SettingsIntent()
}