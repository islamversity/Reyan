package com.islamversity.quran_home.feature.home.di

import com.islamversity.core.Mapper
import com.islamversity.domain.model.surah.SurahStateRepoModel
import com.islamversity.quran_home.feature.home.SavedSurahState
import com.islamversity.quran_home.feature.home.mapper.SurahStateUiMapper
import dagger.Module
import dagger.Provides


@Module
object MapperModule {

    @Provides
    @JvmStatic
    fun bindUISurahStateMapper(): Mapper<SurahStateRepoModel?, SavedSurahState?> =
        SurahStateUiMapper()

}