package com.islamversity.quran_home.feature.onboarding.di

import android.app.Application
import com.bluelinelabs.conductor.Router
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.domain.usecase.DatabaseFillerUseCase
import com.islamversity.navigation.Navigator
import com.islamversity.quran_home.feature.onboarding.*
import dagger.Module
import dagger.Provides

@Module
object OnBoardingModule{

    @Provides
    @JvmStatic
    fun provideNavigator(app: Application, router : Router) : Navigator =
        OnBoardingNavigator(app, router)

    @Provides
    @JvmStatic
    fun provideProcessor(navigator: Navigator, fillerUseCase: DatabaseFillerUseCase) : MviProcessor<OnBoardingIntent, OnBoardingResult> =
        OnBoardingProcessor(navigator, fillerUseCase,)

    @Provides
    @JvmStatic
    fun providePresenter(processor : MviProcessor<OnBoardingIntent, OnBoardingResult>) : MviPresenter<OnBoardingIntent, OnBoardingState> =
        OnBoardingPresenter(processor)
}
