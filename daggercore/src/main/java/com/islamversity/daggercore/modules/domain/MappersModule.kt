package com.islamversity.daggercore.modules.domain

import com.islamversity.core.Mapper
import com.islamversity.db.model.JuzEntity
import com.islamversity.db.model.Surah
import com.islamversity.domain.mapper.JuzDBRepoMapper
import com.islamversity.domain.mapper.SurahDBRepoMapper
import com.islamversity.domain.mappers.CalligraphyEntityDomainMapper
import com.islamversity.domain.model.Calligraphy
import com.islamversity.domain.model.JuzRepoModel
import com.islamversity.domain.model.SurahRepoModel
import com.islamversity.db.Calligraphy as CalligraphyEntity
import dagger.Module
import dagger.Provides

@Module
object MappersModule {

    @JvmStatic
    @Provides
    fun provideCalligraphyEntityDomainMapper() : Mapper<CalligraphyEntity, Calligraphy> =
        CalligraphyEntityDomainMapper()

    @Provides
    @JvmStatic
    fun bindSurahMapper(): Mapper<Surah, SurahRepoModel> = SurahDBRepoMapper()

    @Provides
    @JvmStatic
    fun bindJuzMapper(): Mapper<JuzEntity, JuzRepoModel> = JuzDBRepoMapper()
}