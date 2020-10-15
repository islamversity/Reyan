package com.islamversity.settings

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviResult
import com.islamversity.settings.models.CalligraphyUIModel

sealed class SettingsResult : MviResult {
    object LastStable : SettingsResult()
    class Error(val err: BaseState.ErrorState) : SettingsResult()

    data class SurahNameCalligraphies(val list: List<CalligraphyUIModel>) : SettingsResult()
    data class AyaCalligraphies(val list: List<CalligraphyUIModel>) : SettingsResult()
    data class SecondTranslationCalligraphies(val list: List<CalligraphyUIModel>) : SettingsResult()

    data class QuranFontSize(val fontSize : Int) : SettingsResult()
    data class TranslateFontSize(val fontSize : Int) : SettingsResult()

    data class SurahCalligraphy(val calligraphy : CalligraphyUIModel) : SettingsResult()
    data class FirstAyaTranslationCalligraphy(val calligraphy : CalligraphyUIModel?) : SettingsResult()
    data class SecondTranslationCalligraphy(val calligraphy : CalligraphyUIModel?) : SettingsResult()
}