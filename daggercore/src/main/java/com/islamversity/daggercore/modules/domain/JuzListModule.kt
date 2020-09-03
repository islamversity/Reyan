package com.islamversity.daggercore.modules.domain


import com.islamversity.core.Mapper
import com.islamversity.db.datasource.AyaLocalDataSource
import com.islamversity.db.model.JuzEntity
import com.islamversity.domain.model.JuzRepoModel
import com.islamversity.domain.repo.juz.JuzListRepo
import com.islamversity.domain.repo.juz.JuzListRepoImpl
import dagger.Module
import dagger.Provides

@Module
object JuzListModule {

    @JvmStatic
    @Provides
    fun bindJuzListRepo(
        ayaDataSource: AyaLocalDataSource,
        juzMapper: Mapper<JuzEntity, JuzRepoModel>
    ): JuzListRepo =
        JuzListRepoImpl(
            ayaDataSource,
            juzMapper
        )
}
