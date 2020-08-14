package com.islamversity.search.di

import com.islamversity.core.Mapper
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.domain.model.sora.SoraRepoModel
import com.islamversity.domain.model.sora.SoraUIModel
import com.islamversity.domain.repo.SoraSearchRepo
import com.islamversity.navigation.Navigator
import com.islamversity.search.*
import com.islamversity.search.mapper.SoraRepoUIMapper
import dagger.Module
import dagger.Provides

@Module
object SearchModule {

    @Provides
    @JvmStatic
    fun bindProcessor(
        repo : SoraSearchRepo,
        mapper : Mapper<SoraRepoModel, SoraUIModel>,
        navigator: Navigator
    ): MviProcessor<SearchIntent, SearchResult> =
        SearchProcessor(
            repo = repo,
            mapper = mapper,
            navigator = navigator
        )

    @FeatureScope
    @Provides
    @JvmStatic
    fun bindPresenter(
        processor: MviProcessor<SearchIntent, SearchResult>
    ): MviPresenter<SearchIntent, SearchState> = SearchPresenter(
        processor
    )

    @Provides
    @JvmStatic
    fun bindSoraRepoUIMapper(): Mapper<SoraRepoModel, SoraUIModel> = SoraRepoUIMapper()

}