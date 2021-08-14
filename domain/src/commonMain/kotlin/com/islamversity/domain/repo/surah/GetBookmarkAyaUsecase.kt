package com.islamversity.domain.repo.surah

import com.islamversity.domain.model.surah.ReadingBookmarkRepoModel
import kotlinx.coroutines.flow.Flow

interface GetBookmarkAyaUsecase {
    fun getBookmarkAya(): Flow<ReadingBookmarkRepoModel?>
}

class GetBookmarkAyaUsecaseImpl(private val surahRepo: SurahRepo) : GetBookmarkAyaUsecase {
    override fun getBookmarkAya() = surahRepo.getBookmarkAya()
}