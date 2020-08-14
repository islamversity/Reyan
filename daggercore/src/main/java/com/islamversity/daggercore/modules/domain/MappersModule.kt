package com.islamversity.daggercore.modules.domain

import com.islamversity.core.Mapper
import com.islamversity.db.model.SoraEntityModel
import com.islamversity.domain.mapper.SoraEntityRepoMapper
import com.islamversity.domain.model.sora.SoraRepoModel

import dagger.Module
import dagger.Provides

object MappersModule {

    @Provides
    @JvmStatic
    fun bindPersonServerRepoMapper(): Mapper<SoraEntityModel, SoraRepoModel> = SoraEntityRepoMapper()

}
