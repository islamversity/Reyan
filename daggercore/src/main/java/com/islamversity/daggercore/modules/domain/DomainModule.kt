package com.islamversity.daggercore.modules.domain

import com.islamversity.daggercore.modules.LocalDataSourceModule
import dagger.Module

@Module(
    includes = [
        SurahListModule::class,
        JuzListModule::class,
        SettingsRepoModule::class,
        LicensesRepoModule::class,
        MappersModule::class,
        LocalDataSourceModule::class,
        SearchRepoModule::class,
        AyaModule::class,
        CalligraphyModule::class,
        SurahStateModule::class,
    ]
)
abstract class DomainModule
