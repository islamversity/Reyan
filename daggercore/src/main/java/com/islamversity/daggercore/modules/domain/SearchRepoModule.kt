package com.islamversity.daggercore.modules.domain

import com.islamversity.core.Mapper
import com.islamversity.db.datasource.SoraLocalDataSource
import com.islamversity.db.datasource.SoraLocalDataSourceImpl
import com.islamversity.db.model.Sora
import com.islamversity.domain.model.sora.SoraRepoModel
import com.islamversity.domain.repo.SoraSearchRepo
import com.islamversity.domain.repo.SoraSearchRepoImpl
import dagger.Module
import dagger.Provides

@Module
object SearchRepoModule {

    @JvmStatic
    @Provides
    fun provideDataSource(): SoraLocalDataSource = TODO()

    @JvmStatic
    @Provides
    fun bindSoraSearchRepo(
        dataSource: SoraLocalDataSource,
        mapper: Mapper<Sora, SoraRepoModel>
    ): SoraSearchRepo = SoraSearchRepoImpl(
        dataSource, mapper
    )
}