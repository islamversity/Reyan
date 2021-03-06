package com.islamversity.quran_home.feature.home.mapper

import com.islamversity.core.Mapper
import com.islamversity.domain.model.surah.ReadingBookmarkRepoModel
import com.islamversity.quran_home.feature.home.BookmarkState

class AyaBookmarkRepoStateMapper : Mapper<ReadingBookmarkRepoModel, BookmarkState> {

    override fun map(item: ReadingBookmarkRepoModel): BookmarkState =
        BookmarkState(
            item.pageType.toState(),

            item.juz,
            item.surahId,
            item.surahName,

            item.ayaId,
            item.ayaOrder
        )

    private fun ReadingBookmarkRepoModel.PageType.toState() =
        when(this) {
            ReadingBookmarkRepoModel.PageType.SURAH -> BookmarkState.PageType.SURAH
            ReadingBookmarkRepoModel.PageType.JUZ -> BookmarkState.PageType.JUZ
        }
}