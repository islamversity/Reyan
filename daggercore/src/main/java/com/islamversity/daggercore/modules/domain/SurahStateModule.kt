package com.islamversity.daggercore.modules.domain

import com.islamversity.domain.repo.surah.*
import dagger.Module
import dagger.Provides

@Module
object SurahStateModule {

    @JvmStatic
    @Provides
    fun bindSaveSurahStateUsecase(
        surahRepo: SurahRepo
    ): SaveSurahStateUsecase = SaveSurahStateUsecaseImpl(surahRepo)

    @JvmStatic
    @Provides
    fun bindGetSurahStateUsecase(
        surahRepo: SurahRepo
    ): GetSurahStateUsecase = GetSurahStateUsecaseImpl(surahRepo)
}