package com.islamversity.daggercore.modules.domain

import com.islamversity.core.Mapper
import com.islamversity.db.datasource.SurahLocalDataSource
import com.islamversity.db.model.Surah
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.repo.*
import com.islamversity.domain.repo.surah.GetSurahsUsecase
import com.islamversity.domain.repo.surah.GetSurahsUsecaseImpl
import com.islamversity.domain.repo.surah.SurahListRepo
import com.islamversity.domain.repo.surah.SurahListRepoImpl
import dagger.Module
import dagger.Provides

@Module
object SurahListModule {

    @JvmStatic
    @Provides
    fun bindSurahListRepo(
        dataSource: SurahLocalDataSource,
        mapper: Mapper<Surah, SurahRepoModel>
    ): SurahListRepo =
        SurahListRepoImpl(
            dataSource,
            mapper
        )

    @JvmStatic
    @Provides
    fun bindGetSurahListUsecase(
        surahListRepo: SurahListRepo,
        settingRepo: SettingRepo
    ): GetSurahsUsecase =
        GetSurahsUsecaseImpl(
            surahListRepo,
            settingRepo
        )
}