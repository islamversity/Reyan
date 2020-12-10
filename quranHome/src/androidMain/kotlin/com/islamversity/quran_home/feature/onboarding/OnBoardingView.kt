package com.islamversity.quran_home.feature.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.islamversity.base.CoroutineView
import com.islamversity.base.visible
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.daggercore.CoreComponent
import com.islamversity.quran_home.databinding.OnboardingViewBinding
import com.islamversity.quran_home.feature.onboarding.di.DaggerOnBoardingComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class OnBoardingView : CoroutineView<OnboardingViewBinding, OnBoardingState, OnBoardingIntent>() {

    @Inject
    override lateinit var presenter: MviPresenter<OnBoardingIntent, OnBoardingState>

    override fun injectDependencies(core: CoreComponent) {
        DaggerOnBoardingComponent.factory().create(router, core).inject(this)
    }

    override fun bindView(inflater: LayoutInflater, container: ViewGroup): OnboardingViewBinding =
        OnboardingViewBinding.inflate(inflater, container, false)

    override fun render(state: OnBoardingState) {
        renderError(state.base)
        renderLoading(state.base)

        binding.btnContinue.visibility = if(state.showContinueBtn) View.VISIBLE else View.INVISIBLE
        binding.pbLoadingBar.progress = state.loadingPercent
    }

    override fun intents(): Flow<OnBoardingIntent> =
        flowOf(OnBoardingIntent.Initial)

}