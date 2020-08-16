package com.islamversity.daggercore.modules.domain

import com.islamversity.core.Mapper
import com.islamversity.db.Calligraphy
import com.islamversity.db.datasource.CalligraphyLocalDataSource
import com.islamversity.domain.repo.CalligraphyRepo
import com.islamversity.domain.repo.CalligraphyRepoImpl
import dagger.Module
import dagger.Provides

@Module
object CalligraphyModule {

    @Provides
    @JvmStatic
    fun provideCalligraphyRepoImpl(
        ds : CalligraphyLocalDataSource,
        mapper: Mapper<Calligraphy, com.islamversity.domain.model.Calligraphy>
    ) : CalligraphyRepo =
        CalligraphyRepoImpl(ds,mapper)
}