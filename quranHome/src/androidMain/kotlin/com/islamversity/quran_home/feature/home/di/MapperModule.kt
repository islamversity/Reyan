package com.islamversity.quran_home.feature.home.di

import com.islamversity.core.Mapper
import com.islamversity.domain.model.surah.ReadingBookmarkRepoModel
import com.islamversity.quran_home.feature.home.BookmarkState
import com.islamversity.quran_home.feature.home.mapper.AyaBookmarkRepoStateMapper
import dagger.Module
import dagger.Provides


@Module
object MapperModule {

    @Provides
    @JvmStatic
    fun bindUISurahStateMapper(): Mapper<ReadingBookmarkRepoModel, BookmarkState> =
        AyaBookmarkRepoStateMapper()

}