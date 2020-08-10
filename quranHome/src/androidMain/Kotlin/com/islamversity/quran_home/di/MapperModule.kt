package com.islamversity.quran_home.di

import com.islamversity.core.Mapper
import com.islamversity.domain.model.JuzRepoModel
import com.islamversity.quran_home.mapper.JuzRepoUIMapper
import com.islamversity.quran_home.mapper.SurahRepoUIMapper
import com.islamversity.quran_home.model.JozUIModel
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.quran_home.model.SurahUIModel
import dagger.Module
import dagger.Provides


@Module
object MapperModule {

    @Provides
    @JvmStatic
    fun bindUISurahMapper(): Mapper<SurahRepoModel, SurahUIModel> = SurahRepoUIMapper()

    @Provides
    @JvmStatic
    fun bindUIJuzMapper(): Mapper<JuzRepoModel, JozUIModel> = JuzRepoUIMapper()

}