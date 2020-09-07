package com.islamversity.quran_home.feature.home.di

import com.islamversity.core.mvi.MviPresenter
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.navigation.Navigator
import com.islamversity.quran_home.feature.home.*
import dagger.Module
import dagger.Provides

@Module
object QuranHomeModule {

    @JvmStatic
    @Provides
    @FeatureScope
    fun providePresenter(processor: MviProcessor<QuranHomeIntent, QuranHomeResult>):
            MviPresenter<QuranHomeIntent, QuranHomeState> =
        QuranHomePresenter(processor)

    @JvmStatic
    @Provides
    fun provideProcessor(navigator : Navigator): MviProcessor<QuranHomeIntent, QuranHomeResult> =
        QuranHomeProcessor(navigator)

}