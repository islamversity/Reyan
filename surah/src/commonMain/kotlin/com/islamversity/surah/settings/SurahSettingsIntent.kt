package com.islamversity.surah.settings

import com.islamversity.core.mvi.MviIntent
import com.islamversity.surah.model.CalligraphyUIModel

sealed class SurahSettingsIntent : MviIntent {
    object Initial : SurahSettingsIntent()
    data class NewSurahNameCalligraphySelected(
        val calligraphy : CalligraphyUIModel
    ) : SurahSettingsIntent()

    class NewAyaCalligraphy(
        val language: CalligraphyUIModel
    ) : SurahSettingsIntent()

    class ChangeQuranFontSize(
        val size: Int
    ) : SurahSettingsIntent()

    class ChangeTranslateFontSize(
        val size: Int
    ) : SurahSettingsIntent()
}