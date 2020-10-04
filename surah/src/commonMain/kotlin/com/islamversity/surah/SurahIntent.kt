package com.islamversity.surah

import com.islamversity.core.mvi.MviIntent

sealed class SurahIntent : MviIntent {
    data class Initial(
        val surahId: String,
        val startingAyaPosition : Long,
    ) : SurahIntent()
}
