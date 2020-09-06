package com.islamversity.daggercore.modules.domain

import com.islamversity.core.Mapper
import com.islamversity.db.model.Aya
import com.islamversity.db.model.JuzEntity
import com.islamversity.db.model.Surah
import com.islamversity.domain.mapper.AyaEntityRepoMapper
import com.islamversity.domain.mapper.JuzDBRepoMapper
import com.islamversity.domain.mapper.CalligraphyEntityRepoMapper
import com.islamversity.domain.model.Calligraphy
import com.islamversity.domain.model.JuzRepoModel
import dagger.Module
import dagger.Provides
import com.islamversity.domain.mapper.SurahEntityRepoMapper
import com.islamversity.domain.model.aya.AyaRepoModel

import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.db.Calligraphy as CalligraphyEntity

@Module
object MappersModule {

    @Provides
    @JvmStatic
    fun provideJuzEntityRepoMapper(): Mapper<JuzEntity, JuzRepoModel> = JuzDBRepoMapper()

    @Provides
    @JvmStatic
    fun provideCalligraphyEntityRepoMapper() : Mapper<CalligraphyEntity, Calligraphy> = CalligraphyEntityRepoMapper()

    @Provides
    @JvmStatic
    fun provideSurahEntityRepoMapper(): Mapper<Surah, SurahRepoModel> = SurahEntityRepoMapper()

    @JvmStatic
    @Provides
    fun provideAyaEntityRepoMapper(): Mapper<Aya, AyaRepoModel> = AyaEntityRepoMapper()
}
