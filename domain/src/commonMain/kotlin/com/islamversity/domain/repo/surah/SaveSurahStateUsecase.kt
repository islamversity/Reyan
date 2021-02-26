package com.islamversity.domain.repo.surah

import com.islamversity.domain.model.surah.SurahStateRepoModel
import kotlinx.coroutines.flow.Flow

interface SaveSurahStateUsecase {
    fun saveState(state: SurahStateRepoModel): Flow<Boolean>
}

class SaveSurahStateUsecaseImpl(private val surahRepo: SurahRepo):SaveSurahStateUsecase{
    override fun saveState(state: SurahStateRepoModel) = surahRepo.saveSurahState(state)
}