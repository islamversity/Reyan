package com.islamversity.settings

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState
import com.islamversity.domain.model.QuranReadFontSize
import com.islamversity.settings.models.CalligraphyUIModel

data class SettingsState(
    override val base: BaseState,
    val ayaCalligraphies: List<CalligraphyUIModel>,
    val surahNameCalligraphies: List<CalligraphyUIModel>,
    val secondTranslationCalligraphies: List<CalligraphyUIModel>,
    val selectedAyaCalligraphy: CalligraphyUIModel?,
    val selectedSurahNameCalligraphy: CalligraphyUIModel?,
    val selectedSecondTranslationCalligraphy: CalligraphyUIModel?,
    val quranTextFontSize: Int,
    val translateTextFontSize: Int
) : BaseViewState {
    companion object {
        fun idle() =
            SettingsState(
                base = BaseState.stable(),
                ayaCalligraphies = emptyList(),
                surahNameCalligraphies = emptyList(),
                secondTranslationCalligraphies = emptyList(),
                selectedAyaCalligraphy = null,
                selectedSurahNameCalligraphy = null,
                selectedSecondTranslationCalligraphy = null,
                quranTextFontSize = QuranReadFontSize.DEFAULT.size,
                translateTextFontSize = QuranReadFontSize.DEFAULT.size
            )
    }
}