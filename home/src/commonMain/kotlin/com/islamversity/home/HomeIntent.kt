package com.islamversity.home

import com.islamversity.core.mvi.MviIntent

sealed class HomeIntent : MviIntent {
    object Initial : HomeIntent()
    class SearchClicks(
        val backTransName : String,
        val textTransName : String
    ) : HomeIntent()
}