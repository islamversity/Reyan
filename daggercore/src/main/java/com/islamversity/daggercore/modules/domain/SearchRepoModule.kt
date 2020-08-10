package com.islamversity.daggercore.modules.domain

import com.islamversity.core.Mapper
import com.islamversity.db.datasource.SurahLocalDataSource
import com.islamversity.db.model.Surah
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.domain.repo.surah.SearchSurahNameUseCase
import com.islamversity.domain.repo.surah.SearchSurahNameUseCaseImpl
import com.islamversity.domain.repo.surah.SurahSearchRepo
import com.islamversity.domain.repo.surah.SurahSearchRepoImpl
import dagger.Module
import dagger.Provides

@Module
object SearchRepoModule {

    @JvmStatic
    @Provides
    fun provideSurahSearchRepo(
        dataSource: SurahLocalDataSource,
        mapper: Mapper<Surah, SurahRepoModel>
    ): SurahSearchRepo = SurahSearchRepoImpl(
        dataSource, mapper
    )

    @JvmStatic
    @Provides
    fun provideSearchSurahNameUseCase(
        settingRepo: SettingRepo,
        searchRepo: SurahSearchRepo
    ): SearchSurahNameUseCase = SearchSurahNameUseCaseImpl(
        settingRepo, searchRepo
    )
}