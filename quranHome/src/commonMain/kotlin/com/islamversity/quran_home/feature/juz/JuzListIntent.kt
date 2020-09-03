package com.islamversity.quran_home.feature.juz

import com.islamversity.core.mvi.MviIntent
import com.islamversity.quran_home.feature.juz.model.JozUIModel

sealed class JuzListIntent : MviIntent {
    object Initial : JuzListIntent()
    data class ItemClick(
        val action: JozRowActionModel
    ) : JuzListIntent()
}

data class JozRowActionModel(val juz: JozUIModel)