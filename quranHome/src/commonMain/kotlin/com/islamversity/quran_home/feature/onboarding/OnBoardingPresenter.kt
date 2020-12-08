package com.islamversity.quran_home.feature.onboarding

import com.islamversity.core.FlowBlock
import com.islamversity.core.mvi.BasePresenter
import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.core.notOfType
import com.islamversity.core.ofType
import kotlinx.coroutines.flow.take

class OnBoardingPresenter(
    processor: MviProcessor<OnBoardingIntent, OnBoardingResult>
) : BasePresenter<OnBoardingIntent, OnBoardingState, OnBoardingResult>(
    processor,
    OnBoardingState.idle()
) {
    override fun filterIntent(): List<FlowBlock<OnBoardingIntent, OnBoardingIntent>> =
        listOf(
            {
                this.ofType<OnBoardingIntent.Initial>().take(1)
            },
            {
                notOfType(OnBoardingIntent.Initial::class)
            })

    override fun reduce(preState: OnBoardingState, result: OnBoardingResult) =
        when (result) {
            is OnBoardingResult.Loading ->
                preState.copy(loadingPercent = result.percent)
            OnBoardingResult.InitializingDone ->
                preState.copy(loadingPercent = 100, showContinueBtn = true)
        }
}