package com.islamversity.quran_home.feature.onboarding.di

import com.bluelinelabs.conductor.Router
import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.quran_home.feature.onboarding.OnBoardingView
import dagger.BindsInstance
import dagger.Component

@FeatureScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [OnBoardingModule::class]
)
interface OnBoardingComponent {

    fun inject(view : OnBoardingView)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance router : Router, coreComponent: CoreComponent) : OnBoardingComponent
    }
}