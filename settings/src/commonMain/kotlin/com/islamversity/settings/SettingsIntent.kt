package com.islamversity.settings

import com.islamversity.core.mvi.MviIntent
import com.islamversity.settings.models.CalligraphyUIModel

sealed class SettingsIntent : MviIntent {
    object Initial : SettingsIntent()
    data class NewSecondSurahNameCalligraphySelected(
        val calligraphy: CalligraphyUIModel
    ) : SettingsIntent()

    class NewFirstTranslation(
        val language: CalligraphyUIModel
    ) : SettingsIntent()

    class NewSecondTranslation(
        val language: CalligraphyUIModel
    ) : SettingsIntent()

    class ChangeQuranFontSize(
        val size: Int
    ) : SettingsIntent()

    class ChangeTranslateFontSize(
        val size: Int
    ) : SettingsIntent()
}