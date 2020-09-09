package com.islamversity.quran_home.feature.surah.di

import com.islamversity.core.Mapper
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.quran_home.feature.surah.SurahUIItemMapper
import com.islamversity.quran_home.feature.surah.mapper.SurahRepoUIMapper
import com.islamversity.quran_home.feature.surah.model.SurahUIModel
import com.islamversity.view_component.SurahItemModel
import dagger.Module
import dagger.Provides


@Module
object MapperModule {

    @Provides
    @JvmStatic
    fun bindUISurahMapper(): Mapper<SurahRepoModel, SurahUIModel> =
        SurahRepoUIMapper()

    @Provides
    @JvmStatic
    fun bindSurahUIItemMapper(): Mapper<SurahUIModel, SurahItemModel> =
        SurahUIItemMapper()

}