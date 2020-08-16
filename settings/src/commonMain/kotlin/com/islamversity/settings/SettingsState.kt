package com.islamversity.settings

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState
import com.islamversity.settings.models.CalligraphyUIModel

private const val DEFAULT_QURAN_TEXT_FONT_SIZE = 14

data class SettingsState(
    override val base: BaseState,
    val ayaCalligraphies : List<CalligraphyUIModel>,
    val surahNameCalligraphies : List<CalligraphyUIModel>,
    val selectedAyaCalligraphy : CalligraphyUIModel?,
    val selectedSurahNameCalligraphy : CalligraphyUIModel?,
    val quranTextFontSize : Int
) : BaseViewState {
    companion object {
        fun idle() =
            SettingsState(
                base = BaseState.stable(),
                ayaCalligraphies = emptyList(),
                surahNameCalligraphies = emptyList(),
                selectedAyaCalligraphy = null,
                selectedSurahNameCalligraphy = null,
                quranTextFontSize = DEFAULT_QURAN_TEXT_FONT_SIZE
            )
    }
}