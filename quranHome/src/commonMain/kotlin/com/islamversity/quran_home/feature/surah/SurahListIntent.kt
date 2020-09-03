package com.islamversity.quran_home.feature.surah

import com.islamversity.core.mvi.MviIntent
import com.islamversity.quran_home.feature.surah.model.SurahUIModel

sealed class SurahListIntent : MviIntent {
    object Initial : SurahListIntent()
    data class ItemClick(
        val action: SurahRowActionModel
    ) : SurahListIntent()
}

data class SurahRowActionModel(val surah: SurahUIModel)