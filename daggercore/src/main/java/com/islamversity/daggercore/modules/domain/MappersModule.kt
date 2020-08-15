package com.islamversity.daggercore.modules.domain

import com.islamversity.core.Mapper
import com.islamversity.db.model.Sora
import com.islamversity.domain.mapper.SoraEntityRepoMapper
import com.islamversity.domain.model.sora.SoraRepoModel

import dagger.Module
import dagger.Provides

@Module
object MappersModule {

    @Provides
    @JvmStatic
    fun bindPersonServerRepoMapper(): Mapper<Sora, SoraRepoModel> = SoraEntityRepoMapper()

}
