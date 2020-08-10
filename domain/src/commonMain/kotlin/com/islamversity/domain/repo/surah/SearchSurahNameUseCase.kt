package com.islamversity.domain.repo.surah

import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.repo.SettingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow

interface SearchSurahNameUseCase {
    fun search(query: String): Flow<List<SurahRepoModel>>
}

class SearchSurahNameUseCaseImpl(
    private val settingRepo: SettingRepo,
    private val searchRepo: SurahSearchRepo
) : SearchSurahNameUseCase {
    override fun search(query: String): Flow<List<SurahRepoModel>> =
        flow {
            emit(settingRepo.getCurrentSurahCalligraphy())
        }
            .flatMapMerge {
                searchRepo.search(query, it)
            }
}
