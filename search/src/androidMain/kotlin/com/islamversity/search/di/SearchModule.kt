package com.islamversity.search.di

import com.islamversity.core.Mapper
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.domain.repo.surah.SearchSurahNameUseCase
import com.islamversity.domain.repo.surah.SurahSearchRepo
import com.islamversity.navigation.Navigator
import com.islamversity.search.*
import com.islamversity.search.mapper.SurahRepoUIMapper
import com.islamversity.search.model.SurahUIModel
import com.islamversity.search.view.SurahUIItemMapper
import com.islamversity.view_component.SurahItemModel
import dagger.Module
import dagger.Provides

@Module
object SearchModule {

    @Provides
    @JvmStatic
    fun bindProcessor(
        searchUsecase : SearchSurahNameUseCase,
        mapper : Mapper<SurahRepoModel, SurahUIModel>,
        navigator: Navigator
    ): MviProcessor<SearchIntent, SearchResult> =
        SearchProcessor(
            searchUsecase = searchUsecase,
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
    fun bindSoraRepoUIMapper(): Mapper<SurahRepoModel, SurahUIModel> = SurahRepoUIMapper()

    @Provides
    @JvmStatic
    fun bindSurahUIItemMapper(): Mapper<SurahUIModel, SurahItemModel> = SurahUIItemMapper()

}