package com.islamversity.daggercore.modules.domain


import com.islamversity.core.Mapper
import com.islamversity.db.datasource.SurahLocalDataSource
import com.islamversity.db.model.Surah
import com.islamversity.domain.model.SurahRepoModel
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
        surahDataSource: SurahLocalDataSource,
        surahMapper: Mapper<Surah, SurahRepoModel>
    ): SurahListRepo =
        SurahListRepoImpl(
            surahDataSource,
            surahMapper
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