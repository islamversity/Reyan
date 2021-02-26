package com.islamversity.domain.repo.surah

import com.islamversity.domain.model.surah.SurahStateRepoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface GetSurahStateUsecase {
    fun getState(): Flow<SurahStateRepoModel?>
}

class GetSurahStateUsecaseImpl(private val surahRepo: SurahRepo):GetSurahStateUsecase{
    override fun getState() = surahRepo.getSurahState()
}