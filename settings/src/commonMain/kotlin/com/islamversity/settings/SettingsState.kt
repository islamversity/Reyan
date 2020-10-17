package com.islamversity.settings

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState
import com.islamversity.domain.model.QuranReadFontSize
import com.islamversity.settings.models.CalligraphyUIModel

data class SettingsState(
    override val base: BaseState,
    val surahNameCalligraphies: List<CalligraphyUIModel>,
    val firstTranslationCalligraphies: List<CalligraphyUIModel>,
    val secondTranslationCalligraphies: List<CalligraphyUIModel>,
    val selectedSurahNameCalligraphy: CalligraphyUIModel?,
    val selectedFirstTranslationCalligraphy: CalligraphyUIModel?,
    val selectedSecondTranslationCalligraphy: CalligraphyUIModel?,
    val quranTextFontSize: Int,
    val translateTextFontSize: Int
) : BaseViewState {
    companion object {
        fun idle() =
            SettingsState(
                base = BaseState.stable(),
                surahNameCalligraphies = emptyList(),
                firstTranslationCalligraphies = emptyList(),
                secondTranslationCalligraphies = emptyList(),
                selectedSurahNameCalligraphy = null,
                selectedFirstTranslationCalligraphy = null,
                selectedSecondTranslationCalligraphy = null,
                quranTextFontSize = QuranReadFontSize.DEFAULT.size,
                translateTextFontSize = QuranReadFontSize.DEFAULT.size
            )
    }
}