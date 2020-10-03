package com.islamversity.daggercore.modules.domain

import com.islamversity.core.Mapper
import com.islamversity.db.datasource.CalligraphyLocalDataSource
import com.islamversity.db.datasource.SurahLocalDataSource
import com.islamversity.db.model.SurahWithTwoName
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.domain.repo.surah.GetSurahUsecase
import com.islamversity.domain.repo.surah.GetSurahUsecaseImpl
import com.islamversity.domain.repo.surah.SurahRepo
import com.islamversity.domain.repo.surah.SurahRepoImpl
import dagger.Module
import dagger.Provides

@Module
object SurahListModule {

    @JvmStatic
    @Provides
    fun bindSurahListRepo(
        dataSource: SurahLocalDataSource,
        mapper: Mapper<SurahWithTwoName, SurahRepoModel>
    ): SurahRepo =
        SurahRepoImpl(
            dataSource,
            mapper
        )

    @JvmStatic
    @Provides
    fun bindGetSurahListUsecase(
        surahRepo: SurahRepo,
        settingRepo: SettingRepo,
        calligraphyDS: CalligraphyLocalDataSource,
    ): GetSurahUsecase =
        GetSurahUsecaseImpl(
            surahRepo,
            settingRepo,
            calligraphyDS
        )
}
