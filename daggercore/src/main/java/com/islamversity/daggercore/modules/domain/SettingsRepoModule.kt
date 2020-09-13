package com.islamversity.daggercore.modules.domain

import com.islamversity.core.Mapper
import com.islamversity.db.datasource.CalligraphyLocalDataSource
import com.islamversity.db.datasource.SettingsDataSource
import com.islamversity.domain.model.Calligraphy
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.domain.repo.SettingRepoImpl
import dagger.Module
import dagger.Provides
import com.islamversity.db.Calligraphy as CalligraphyEntity

@Module
object SettingsRepoModule {

    @Provides
    @JvmStatic
    fun provideSettingsRepo(
        calligraphyLocalDataSource: CalligraphyLocalDataSource,
        mapper: Mapper<CalligraphyEntity, Calligraphy>,
        settingsDataSource: SettingsDataSource,
    ): SettingRepo =
        SettingRepoImpl(settingsDataSource, calligraphyLocalDataSource, mapper)
}