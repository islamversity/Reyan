package com.islamversity.surah.settings

import com.islamversity.domain.model.QuranReadFontSize
import com.islamversity.surah.model.CalligraphyUIModel

data class SurahSettingsState(
    val ayaCalligraphies : List<CalligraphyUIModel> = emptyList(),
    val surahNameCalligraphies : List<CalligraphyUIModel> = emptyList(),
    val selectedFirstTranslationAyaCalligraphy : CalligraphyUIModel? = null,
    val selectedSecondTranslationAyaCalligraphy : CalligraphyUIModel? = null,
    val quranTextFontSize : Int = QuranReadFontSize.DEFAULT.size,
    val translateTextFontSize : Int = QuranReadFontSize.DEFAULT.size,
    val ayaToolbarVisible : Boolean = false
)