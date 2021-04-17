package com.islamversity.quran_home.feature.home.di

import com.islamversity.core.Mapper
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.domain.model.surah.ReadingBookmarkRepoModel
import com.islamversity.domain.repo.surah.GetBookmarkAyaUsecase
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
        getBookmarkAyaUsecase: GetBookmarkAyaUsecase,
        stateMapper: Mapper<ReadingBookmarkRepoModel, BookmarkState>
    ): MviProcessor<QuranHomeIntent, QuranHomeResult> =
        QuranHomeProcessor(navigator,getBookmarkAyaUsecase,stateMapper)

}