package com.islamversity.quran_home.di

import com.islamversity.core.Mapper
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.domain.model.JuzRepoModel
import com.islamversity.domain.model.SurahRepoModel
import com.islamversity.domain.repo.juz.JuzListRepo
import com.islamversity.domain.repo.surah.GetSurahsUsecase
import com.islamversity.navigation.Navigator
import com.islamversity.quran_home.*
import com.islamversity.quran_home.model.JozUIModel
import com.islamversity.quran_home.model.SurahUIModel
import dagger.Module
import dagger.Provides

@Module
object QuranHomeModule {

    @JvmStatic
    @Provides
    @FeatureScope
    fun providePresenter(processor: MviProcessor<QuranHomeIntent, QuranHomeResult>):
            MviPresenter<QuranHomeIntent, QuranHomeState> = QuranHomePresenter(processor)

    @JvmStatic
    @Provides
    fun provideProcessor(
        navigator: Navigator,
        juzListRepo: JuzListRepo,
        surahsUsecase: GetSurahsUsecase,
        surahMapper: Mapper<SurahRepoModel, SurahUIModel>,
        juzMapper: Mapper<JuzRepoModel, JozUIModel>
    ): MviProcessor<QuranHomeIntent, QuranHomeResult> =
        QuranHomeProcessor(navigator, surahsUsecase, juzListRepo, surahMapper, juzMapper)


}