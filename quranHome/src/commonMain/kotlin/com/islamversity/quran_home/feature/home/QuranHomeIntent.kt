package com.islamversity.quran_home.feature.home

import com.islamversity.core.mvi.MviIntent

sealed class QuranHomeIntent : MviIntent {
    object Initial : QuranHomeIntent()
    object SearchClicked : QuranHomeIntent()
    object SettingsClicked : QuranHomeIntent()
    data class LastVisitClicked(val state: SavedSurahState) : QuranHomeIntent()
}