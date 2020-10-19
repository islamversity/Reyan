package com.islamversity.settings

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState
import com.islamversity.domain.model.QuranReadFontSize
import com.islamversity.settings.models.CalligraphyUIModel

data class SettingsState(
    override val base: BaseState,
    val secondSurahNameCalligraphies: List<CalligraphyUIModel>,
    val firstTranslationCalligraphies: List<CalligraphyUIModel>,
    val secondTranslationCalligraphies: List<CalligraphyUIModel>,
    val selectedSecondSurahNameCalligraphy: CalligraphyUIModel?,
    val selectedFirstTranslationCalligraphy: CalligraphyUIModel?,
    val selectedSecondTranslationCalligraphy: CalligraphyUIModel?,
    val quranTextFontSize: Int,
    val translateTextFontSize: Int
) : BaseViewState {
    companion object {
        fun idle() =
            SettingsState(
                base = BaseState.stable(),
                secondSurahNameCalligraphies = emptyList(),
                firstTranslationCalligraphies = emptyList(),
                secondTranslationCalligraphies = emptyList(),
                selectedSecondSurahNameCalligraphy = null,
                selectedFirstTranslationCalligraphy = null,
                selectedSecondTranslationCalligraphy = null,
                quranTextFontSize = QuranReadFontSize.DEFAULT.size,
                translateTextFontSize = QuranReadFontSize.DEFAULT.size
            )
    }
}