package com.islamversity.surah

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviResult
import com.islamversity.surah.model.CalligraphyUIModel
import com.islamversity.surah.model.UIItem

sealed class SurahResult : MviResult {
    object LastStable : SurahResult()
    object Loading : SurahResult()

    data class Error(val err: BaseState.ErrorState) : SurahResult()

    data class Items(
        val items: List<UIItem>
    ) : SurahResult()

    data class ShowAyaNumber(
        val position: Int,
        val id: String,
        val orderID: Long,
    ) : SurahResult()

    data class MainAyaFontSize(
        val size: Int
    ) : SurahResult()

    data class TranslationFontSize(
        val size: Int
    ) : SurahResult()

    sealed class Settings : SurahResult() {

        data class TranslationCalligraphies(val list: List<CalligraphyUIModel>) : Settings()

        data class QuranFontSize(val fontSize: Int) : Settings()
        data class TranslateFontSize(val fontSize: Int) : Settings()

        data class FirstAyaTranslationCalligraphy(val calligraphy: CalligraphyUIModel?) : Settings()
        data class SecondAyaTranslationCalligraphy(val calligraphy: CalligraphyUIModel?) : Settings()
    }
}
