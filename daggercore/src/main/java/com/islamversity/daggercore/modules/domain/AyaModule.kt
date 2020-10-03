package com.islamversity.daggercore.modules.domain

import com.islamversity.core.Mapper
import com.islamversity.db.datasource.AyaLocalDataSource
import com.islamversity.db.model.Aya
import com.islamversity.domain.model.aya.AyaRepoModel
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.domain.repo.aya.AyaListRepo
import com.islamversity.domain.repo.aya.AyaListRepoImpl
import com.islamversity.domain.repo.aya.GetAyaUseCase
import com.islamversity.domain.repo.aya.GetAyaUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
object AyaModule {
    @JvmStatic
    @Provides
    fun provideAyaListRepo(
        dataSource: AyaLocalDataSource,
        mapper: Mapper<Aya, AyaRepoModel>,
    ): AyaListRepo =
        AyaListRepoImpl(
            dataSource,
            mapper
        )

    @JvmStatic
    @Provides
    fun provideGetAyaUseCase(
        repo: AyaListRepo,
        settingsRepo: SettingRepo
    ): GetAyaUseCase =
        GetAyaUseCaseImpl(repo, settingsRepo)
}
