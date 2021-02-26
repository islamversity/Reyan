package com.islamversity.quran_home.feature.home.di

import com.islamversity.core.Mapper
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.domain.model.surah.SurahStateRepoModel
import com.islamversity.domain.repo.surah.GetSurahStateUsecase
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
    fun provideProcessor(
        navigator : Navigator,
        getSurahStateUsecase: GetSurahStateUsecase,
        stateMapper: Mapper<SurahStateRepoModel?, SavedSurahState?>
    ): MviProcessor<QuranHomeIntent, QuranHomeResult> =
        QuranHomeProcessor(navigator,getSurahStateUsecase,stateMapper)

}