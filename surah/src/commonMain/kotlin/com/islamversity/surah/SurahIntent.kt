package com.islamversity.surah

import com.islamversity.core.mvi.MviIntent
import com.islamversity.navigation.model.BismillahType

sealed class SurahIntent : MviIntent {
    data class Initial(
        val surahId: String,
        val startingAyaPosition : Int,
        val bismillahType: BismillahType
    ) : SurahIntent()
}
