package com.islamversity.quran_home.feature.juz.di

import com.islamversity.core.Mapper
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.domain.model.JuzRepoModel
import com.islamversity.domain.repo.juz.JuzListRepo
import com.islamversity.navigation.Navigator
import com.islamversity.quran_home.feature.juz.*
import com.islamversity.quran_home.feature.juz.model.JozUIModel
import dagger.Module
import dagger.Provides

@Module
object JuzListModule {

    @JvmStatic
    @Provides
    @FeatureScope
    fun providePresenter(processor: MviProcessor<JuzListIntent, JuzListResult>):
            MviPresenter<JuzListIntent, JuzListState> =
        JuzListPresenter(processor)

    @JvmStatic
    @Provides
    fun provideProcessor(
        navigator: Navigator,
        juzListRepo: JuzListRepo,
        juzMapper: Mapper<JuzRepoModel, JozUIModel>
    ): MviProcessor<JuzListIntent, JuzListResult> =
        JuzListProcessor(
            navigator,
            juzListRepo,
            juzMapper
        )


}