package com.islamversity.daggercore.modules.domain

import dagger.Module

@Module(
    includes = [
        SearchRepoModule::class,
        MappersModule::class
    ]
)
abstract class DomainModule
