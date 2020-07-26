package com.islamversity.search.di

import com.islamversity.core.mvi.MviPresenter
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.navigation.Navigator
import com.islamversity.search.*
import dagger.Module
import dagger.Provides

@Module
object SearchModule {

    @Provides
    @JvmStatic
    fun bindProcessor(
        navigator: Navigator
    ): MviProcessor<SearchIntent, SearchResult> =
        SearchProcessor(
            navigator
        )

    @FeatureScope
    @Provides
    @JvmStatic
    fun bindPresenter(
        processor: MviProcessor<SearchIntent, SearchResult>
    ): MviPresenter<SearchIntent, SearchState> = SearchPresenter(
        processor
    )
}