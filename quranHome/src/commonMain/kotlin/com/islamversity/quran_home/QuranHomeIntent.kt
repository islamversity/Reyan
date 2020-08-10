package com.islamversity.quran_home

import com.islamversity.core.mvi.MviIntent
import com.islamversity.quran_home.model.JozUIModel
import com.islamversity.quran_home.model.SurahUIModel

sealed class QuranHomeIntent : MviIntent {
    object Initial : QuranHomeIntent()

    data class ItemClick(
        val action: SurahRowActionModel
    ) : QuranHomeIntent()

    data class JozItemClick(
        val action: JozRowActionModel
    ) : QuranHomeIntent()

    object SearchClicked : QuranHomeIntent()
}

data class SurahRowActionModel(val surah: SurahUIModel)
data class JozRowActionModel(val juz: JozUIModel)