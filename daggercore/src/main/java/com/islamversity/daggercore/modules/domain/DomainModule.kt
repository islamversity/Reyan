package com.islamversity.daggercore.modules.domain

import com.islamversity.daggercore.modules.LocalDataSourceModule
import dagger.Module

@Module(
    includes = [
        SurahListModule::class,
        JuzListModule::class,
        SettingsRepoModule::class,
        MappersModule::class,
        LocalDataSourceModule::class,
        SearchRepoModule::class
    ]
)
abstract class DomainModule
