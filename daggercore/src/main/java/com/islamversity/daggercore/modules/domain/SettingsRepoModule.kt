package com.islamversity.daggercore.modules.domain

import android.app.Application
import com.islamversity.core.Mapper
import com.islamversity.db.Calligraphy as CalligraphyEntity
import com.islamversity.db.datasource.CalligraphyLocalDataSource
import com.islamversity.domain.model.Calligraphy
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.domain.repo.SettingRepoImpl
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import dagger.Module
import dagger.Provides


@Module
object SettingsRepoModule {

    @JvmStatic
    @Provides
    fun provideSettings(app: Application): Settings =
        AndroidSettings.Factory(app).create("settings")

    @Provides
    @JvmStatic
    fun provideSettingsRepo(
        settings: Settings,
        calligraphyLocalDataSource: CalligraphyLocalDataSource,
        mapper: Mapper<CalligraphyEntity, Calligraphy>
    ): SettingRepo =
        SettingRepoImpl(settings, calligraphyLocalDataSource, mapper)
}