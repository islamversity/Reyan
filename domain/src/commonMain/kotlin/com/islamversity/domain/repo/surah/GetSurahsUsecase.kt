package com.islamversity.domain.repo.surah

import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.domain.repo.aya.AyaListRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


interface GetSurahsUsecase {
    fun getSurahs(): Flow<List<SurahRepoModel>>
}

class GetSurahsUsecaseImpl(
    private val surahListRepo: SurahListRepo,
    private val ayaListRepo: AyaListRepo,
    private val settingRepo: SettingRepo
) : GetSurahsUsecase {
    override fun getSurahs() =
        flow {
            emit(settingRepo.getCurrentSurahCalligraphy())
        }
            .flatMapMerge {
                surahListRepo.getAllSurah(it).map { surahList ->
                    surahList.map { surah ->
                        ayaListRepo.observeAllAyas(surah.id, it.id).map { ayaList ->
                            surah.ayaCount = ayaList.size
                        }
                        surah
                    }
                }
            }

}