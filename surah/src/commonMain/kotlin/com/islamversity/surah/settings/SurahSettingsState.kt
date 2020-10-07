package com.islamversity.surah.settings

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState
import com.islamversity.domain.model.QuranReadFontSize
import com.islamversity.surah.model.CalligraphyUIModel

data class SurahSettingsState(
    override val base: BaseState,
    val ayaCalligraphies : List<CalligraphyUIModel>,
    val surahNameCalligraphies : List<CalligraphyUIModel>,
    val selectedAyaCalligraphy : CalligraphyUIModel?,
    val selectedSurahNameCalligraphy : CalligraphyUIModel?,
    val quranTextFontSize : Int,
    val translateTextFontSize : Int
) : BaseViewState {
    companion object {
        fun idle() =
            SurahSettingsState(
                base = BaseState.stable(),
                ayaCalligraphies = emptyList(),
                surahNameCalligraphies = emptyList(),
                selectedAyaCalligraphy = null,
                selectedSurahNameCalligraphy = null,
                quranTextFontSize = QuranReadFontSize.DEFAULT.size,
                translateTextFontSize = QuranReadFontSize.DEFAULT.size
            )
    }
}