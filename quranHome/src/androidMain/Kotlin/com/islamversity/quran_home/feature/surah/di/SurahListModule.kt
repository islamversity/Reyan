package com.islamversity.quran_home.feature.surah.di

import com.islamversity.core.Mapper
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.repo.surah.GetSurahsUsecase
import com.islamversity.navigation.Navigator
import com.islamversity.quran_home.feature.surah.*
import com.islamversity.quran_home.feature.surah.model.SurahUIModel
import dagger.Module
import dagger.Provides

@Module
object SurahListModule {

    @JvmStatic
    @Provides
    @FeatureScope
    fun providePresenter(processor: MviProcessor<SurahListIntent, SurahListResult>):
            MviPresenter<SurahListIntent, SurahListState> =
        SurahListPresenter(processor)

    @JvmStatic
    @Provides
    fun provideProcessor(
        navigator: Navigator,
        surahsUsecase: GetSurahsUsecase,
        surahMapper: Mapper<SurahRepoModel, SurahUIModel>
    ): MviProcessor<SurahListIntent, SurahListResult> =
        SurahListProcessor(
           navigator,
            surahsUsecase,
            surahMapper
        )


}