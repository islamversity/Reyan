package com.islamversity.daggercore.modules.domain

import com.islamversity.core.Mapper
import com.islamversity.domain.mappers.CalligraphyEntityDomainMapper
import com.islamversity.domain.model.Calligraphy
import com.islamversity.db.Calligraphy as CalligraphyEntity
import dagger.Module
import dagger.Provides

@Module
object MappersModule {

    @JvmStatic
    @Provides
    fun provideCalligraphyEntityDomainMapper() : Mapper<CalligraphyEntity, Calligraphy> =
        CalligraphyEntityDomainMapper()
}