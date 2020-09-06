package com.islamversity.surah

import com.islamversity.core.mvi.MviIntent

sealed class SurahIntent : MviIntent {
    object Initial : SurahIntent()
}