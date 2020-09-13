package com.islamversity.domain.repo.surah

import com.islamversity.domain.model.surah.SurahID
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.repo.SettingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow

interface GetSurahUsecase {
    fun getSurahs(): Flow<List<SurahRepoModel>>

    fun getSurah(id: SurahID): Flow<SurahRepoModel?>
}

class GetSurahUsecaseImpl(
    private val surahRepo: SurahRepo,
    private val settingRepo: SettingRepo
) : GetSurahUsecase {
    override fun getSurahs() =
        settingRepo.getCurrentSurahCalligraphy()
            .flatMapMerge {
                surahRepo.getAllSurah(it.id)
            }

    override fun getSurah(id: SurahID): Flow<SurahRepoModel?> =
        settingRepo.getCurrentSurahCalligraphy()
            .flatMapMerge {
                surahRepo.getSurah(id, it.id)
            }
}
