package com.islamversity.quran_home.feature.onboarding

import com.islamversity.core.mvi.MviIntent
import com.islamversity.quran_home.feature.juz.model.JozUIModel

sealed class OnBoardingIntent : MviIntent {
    object Initial : OnBoardingIntent()
}
