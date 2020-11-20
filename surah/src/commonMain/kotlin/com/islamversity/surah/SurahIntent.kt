package com.islamversity.surah

import com.islamversity.core.mvi.MviIntent
import com.islamversity.surah.model.CalligraphyUIModel

sealed class SurahIntent : MviIntent {
    data class Initial(
        val surahId: String,
        val startingAyaPosition: Long,
    ) : SurahIntent()

    sealed class ChangeSettings : SurahIntent() {
        data class QuranFontSize(
            val size: Int
        ) : ChangeSettings()

        data class TranslateFontSize(
            val size: Int
        ) : ChangeSettings()

        data class NewFirstTranslation(
            val language: CalligraphyUIModel
        ) : ChangeSettings()

        data class NewSecondTranslation(
            val language: CalligraphyUIModel
        ) : ChangeSettings()

        data class ShowAyaTollbar(
            val enable: Boolean
        ) : ChangeSettings()
    }
}
