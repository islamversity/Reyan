package com.islamversity.quran_home.feature.surah.di

import com.islamversity.core.Mapper
import com.islamversity.domain.model.SurahRepoModel
import com.islamversity.quran_home.feature.surah.mapper.SurahRepoUIMapper
import com.islamversity.quran_home.feature.surah.model.SurahUIModel
import dagger.Module
import dagger.Provides


@Module
object MapperModule {

    @Provides
    @JvmStatic
    fun bindUISurahMapper(): Mapper<SurahRepoModel, SurahUIModel> =
        SurahRepoUIMapper()

}