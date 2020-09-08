package com.islamversity.daggercore.modules

import com.islamversity.db.*
import com.islamversity.db.datasource.*
import dagger.Module
import dagger.Provides

@Module
object LocalDataSourceModule {

    @JvmStatic
    @Provides
    fun provideCalligraphyDS(queries : CalligraphyQueries) : CalligraphyLocalDataSource =
        CalligraphyLocalDataSourceImpl(queries)

    @JvmStatic
    @Provides
    fun provideSurahDataSource(
        surahQueries: SurahQueries,
        nameQueries: NameQueries
    ): SurahLocalDataSource =
        SurahLocalDataSourceImpl(surahQueries, nameQueries)

    @JvmStatic
    @Provides
    fun provideAyaDataSource(
        ayaQueries: AyaQueries,
        ayaContentQueries: AyaContentQueries
    ): AyaLocalDataSource =
        AyaLocalDataSourceImpl(ayaQueries, ayaContentQueries)

    @JvmStatic
    @Provides
    fun provideBismillah(
        bismillahQueries: BismillahQueries
    ) : BismillahLocalDataSource =
        BismillahLocalDataSourceImpl(bismillahQueries)

}