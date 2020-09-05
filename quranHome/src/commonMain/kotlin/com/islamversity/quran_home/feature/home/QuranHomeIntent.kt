package com.islamversity.quran_home.feature.home

import com.islamversity.core.mvi.MviIntent

sealed class QuranHomeIntent : MviIntent {
    data class SelectTab(val position: Int) : QuranHomeIntent()

    object Initial : QuranHomeIntent()
}