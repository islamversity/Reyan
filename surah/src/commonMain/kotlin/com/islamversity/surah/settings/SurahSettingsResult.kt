package com.islamversity.surah.settings

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviResult
import com.islamversity.surah.model.CalligraphyUIModel

sealed class SurahSettingsResult : MviResult {
    object LastStable : SurahSettingsResult()
    class Error(val err: BaseState.ErrorState) : SurahSettingsResult()

    data class SurahNameCalligraphies(val list: List<CalligraphyUIModel>) : SurahSettingsResult()
    data class AyaCalligraphies(val list: List<CalligraphyUIModel>) : SurahSettingsResult()

    data class QuranFontSize(val fontSize : Int) : SurahSettingsResult()
    data class TranslateFontSize(val fontSize : Int) : SurahSettingsResult()

    data class SurahCalligraphy(val calligraphy : CalligraphyUIModel) : SurahSettingsResult()
    data class FirstAyaTranslationCalligraphy(val calligraphy : CalligraphyUIModel?) : SurahSettingsResult()
}