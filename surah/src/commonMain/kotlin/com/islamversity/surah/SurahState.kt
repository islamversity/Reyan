package com.islamversity.surah

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState
import com.islamversity.domain.model.QuranReadFontSize
import com.islamversity.surah.model.UIItem
import com.islamversity.surah.settings.SurahSettingsState

data class SurahState(
        override val base: BaseState,
        val showBismillah: Boolean,
        val bismillah: String,
        val mainAyaFontSize: Int,
        val translationFontSize: Int,
        val items: List<UIItem>,
        val startFrom: Int,
        val closeScreen: Boolean,
        val scrollToAya: ScrollToAya?,
        val settingsState: SurahSettingsState,
) : BaseViewState {
    companion object {
        fun idle() =
                SurahState(
                        base = BaseState.stable(),
                        items = emptyList(),
                        startFrom = 0,
                        showBismillah = false,
                        bismillah = "",
                        closeScreen = false,
                        scrollToAya = null,
                        settingsState = SurahSettingsState(),
                        mainAyaFontSize = QuranReadFontSize.DEFAULT.size,
                        translationFontSize = QuranReadFontSize.DEFAULT.size,
                )
    }
}

data class ScrollToAya(
        val id: String,
        val order: Long,
        val position: Int,
)