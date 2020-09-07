package com.islamversity.quran_home.feature.juz.di

import com.islamversity.core.Mapper
import com.islamversity.domain.model.JuzRepoModel
import com.islamversity.quran_home.feature.juz.mapper.JuzRepoUIMapper
import com.islamversity.quran_home.feature.juz.model.JozUIModel
import dagger.Module
import dagger.Provides


@Module
object MapperModule {

    @Provides
    @JvmStatic
    fun bindUIJuzMapper(): Mapper<JuzRepoModel, JozUIModel> =
        JuzRepoUIMapper()

}