package com.islamversity.domain.repo.surah

import com.islamversity.domain.model.SurahRepoModel
import com.islamversity.domain.repo.SettingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow


interface GetSurahsUsecase {
    fun getSurahs(): Flow<List<SurahRepoModel>>
}

class GetSurahsUsecaseImpl(
    private val surahListRepo: SurahListRepo,
    private val settingRepo: SettingRepo
) : GetSurahsUsecase {
    override fun getSurahs() =
        flow {
            emit(settingRepo.getCurrentSurahCalligraphy())
        }
            .flatMapMerge {
                surahListRepo.getAllSurah(it)
            }

}