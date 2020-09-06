package com.islamversity.surah.di

import com.islamversity.core.mvi.MviPresenter
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.navigation.Navigator
import com.islamversity.surah.*
import dagger.Module
import dagger.Provides

@Module
object SurahModule {

    @Provides
    @JvmStatic
    fun bindProcessor(
        navigator: Navigator
    ): MviProcessor<SurahIntent, SurahResult> =
        SurahProcessor(
            navigator = navigator
        )

    @FeatureScope
    @Provides
    @JvmStatic
    fun bindPresenter(
        processor: MviProcessor<SurahIntent, SurahResult>
    ): MviPresenter<SurahIntent, SurahState> = SurahPresenter(
        processor
    )
}