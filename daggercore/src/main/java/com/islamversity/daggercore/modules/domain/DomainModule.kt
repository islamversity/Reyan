package com.islamversity.daggercore.modules.domain

import dagger.Module

@Module(
    includes = [
        SearchRepoModule::class
    ]
)
abstract class DomainModule
