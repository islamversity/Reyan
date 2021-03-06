package com.islamversity.domain.repo.surah

import com.islamversity.domain.model.surah.ReadingBookmarkRepoModel

interface BookmarkAyaUsecase {
    suspend fun saveBookmark(state: ReadingBookmarkRepoModel)
}

class BookmarkAyaUsecaseImpl(private val surahRepo: SurahRepo):BookmarkAyaUsecase{
    override suspend fun saveBookmark(state: ReadingBookmarkRepoModel) {
        surahRepo.saveBookmarkAya(state)
    }
}