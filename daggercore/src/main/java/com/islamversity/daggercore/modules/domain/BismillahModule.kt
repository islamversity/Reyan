package com.islamversity.daggercore.modules.domain

import com.islamversity.core.Mapper
import com.islamversity.db.datasource.BismillahLocalDataSource
import com.islamversity.db.model.Bismillah
import com.islamversity.domain.mapper.BismillahEntityRepoMapper
import com.islamversity.domain.model.surah.BismillahRepoModel
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.domain.repo.bismillah.BismillahRepo
import com.islamversity.domain.repo.bismillah.BismillahRepoImpl
import com.islamversity.domain.repo.bismillah.BismillahUsecase
import com.islamversity.domain.repo.bismillah.BismillahUsecaseImpl
import dagger.Module
import dagger.Provides

@Module
object BismillahModule {

    @JvmStatic
    @Provides
    fun provideBismillahEntityRepoMapper(): Mapper<Bismillah, BismillahRepoModel> =
        BismillahEntityRepoMapper()

    @JvmStatic
    @Provides
    fun provideBismillahRepo(
        dataSource: BismillahLocalDataSource,
        mapper: Mapper<Bismillah, BismillahRepoModel>,
    ): BismillahRepo =
        BismillahRepoImpl(dataSource, mapper)

    @JvmStatic
    @Provides
    fun provideBismillahUsecase(
        repo: BismillahRepo,
        settingRepo: SettingRepo,
    ): BismillahUsecase =
        BismillahUsecaseImpl(repo, settingRepo)
}
