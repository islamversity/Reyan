package com.islamversity.quran_home.feature.onboarding

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState
import com.islamversity.quran_home.feature.juz.model.JozUIModel

data class OnBoardingState(
    override val base: BaseState,
    val loadingPercent : Int,
    val showContinueBtn : Boolean
) : BaseViewState {
    companion object {
        fun idle() =
            OnBoardingState(
                base = BaseState.stable(),
                loadingPercent = 0,
                showContinueBtn = false
            )
    }
}