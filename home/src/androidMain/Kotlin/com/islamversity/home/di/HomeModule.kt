package com.islamversity.home.di

import com.islamversity.core.mvi.MviPresenter
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.home.*
import com.islamversity.navigation.Navigator
import dagger.Module
import dagger.Provides

@Module
object HomeModule {
    @JvmStatic
    @Provides
    @FeatureScope
    fun providePresenter(
        processor: MviProcessor<HomeIntent, HomeResult>
    ): MviPresenter<HomeIntent, HomeState> = HomePresenter(
        processor
    )

    @JvmStatic
    @Provides
    fun provideProcessor(
        navigator: Navigator
    ): MviProcessor<HomeIntent, HomeResult> = HomeProcessor(
        navigator
    )
}