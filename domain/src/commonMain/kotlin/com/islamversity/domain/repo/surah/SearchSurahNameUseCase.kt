package com.islamversity.domain.repo.surah

import com.islamversity.db.datasource.CalligraphyLocalDataSource
import com.islamversity.domain.model.CalligraphyId
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.repo.CalligraphyRepo
import com.islamversity.domain.repo.SettingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

interface SearchSurahNameUseCase {
    fun search(query: String): Flow<List<SurahRepoModel>>
}

class SearchSurahNameUseCaseImpl(
    private val settingRepo: SettingRepo,
    private val searchRepo: SurahSearchRepo,
    private val calligraphyDS: CalligraphyLocalDataSource,
) : SearchSurahNameUseCase {
    override fun search(query: String): Flow<List<SurahRepoModel>> =
        calligraphyDS.getArabicSurahCalligraphy().combineTransform(settingRepo.getCurrentSurahCalligraphy()) { arabic, main ->
            emit(arabic to main)
        }
            .flatMapMerge {
                searchRepo.search(query, arabicCalligraphy = CalligraphyId(it.first.id.id), mainCalligraphy = it.second.id)
            }
}
