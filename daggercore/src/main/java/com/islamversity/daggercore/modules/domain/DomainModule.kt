package com.islamversity.daggercore.modules.domain

import com.islamversity.daggercore.modules.LocalDataSourceModule
import dagger.Module

@Module(
    includes = [
        SurahListModule::class,
        JuzListModule::class,
        SettingsModule::class,
        MappersModule::class,
        LocalDataSourceModule::class
    ]
)
abstract class DomainModule
