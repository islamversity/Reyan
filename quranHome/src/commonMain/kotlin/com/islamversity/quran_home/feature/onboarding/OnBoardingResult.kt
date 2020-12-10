package com.islamversity.quran_home.feature.onboarding

import com.islamversity.core.mvi.MviResult

sealed class OnBoardingResult : MviResult {
    data class Loading(val percent : Int) : OnBoardingResult()
    object InitializingDone : OnBoardingResult()
}