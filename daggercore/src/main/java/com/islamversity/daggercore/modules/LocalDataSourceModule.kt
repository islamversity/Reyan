package com.islamversity.daggercore.modules

import com.islamversity.db.CalligraphyQueries
import com.islamversity.db.datasource.CalligraphyLocalDataSource
import com.islamversity.db.datasource.CalligraphyLocalDataSourceImpl
import dagger.Module
import dagger.Provides

@Module
object LocalDataSourceModule {

    @JvmStatic
    @Provides
    fun provideCalligraphyDS(queries : CalligraphyQueries) : CalligraphyLocalDataSource =
        CalligraphyLocalDataSourceImpl(queries)
}