package com.islamversity.domain.repo.aya

import com.islamversity.domain.model.aya.AyaRepoModel
import com.islamversity.domain.model.surah.SurahID
import com.islamversity.domain.repo.CalligraphyRepo
import com.islamversity.domain.repo.SettingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge

interface GetAyaUseCase {
    fun observeAyaMain(surahID: SurahID): Flow<List<AyaRepoModel>>
    fun observeFirstAyaTranslation(surahID: SurahID): Flow<List<AyaRepoModel>>
}

class GetAyaUseCaseImpl(
    private val ayaListRepo: AyaListRepo,
    private val settingRepo: SettingRepo,
    private val calligraphyRepo: CalligraphyRepo,
) : GetAyaUseCase {
    override fun observeAyaMain(surahID: SurahID): Flow<List<AyaRepoModel>> =
        calligraphyRepo.getMainAyaCalligraphy()
            .flatMapMerge {
                ayaListRepo.observeAllAyas(surahID, it.id)
            }

    override fun observeFirstAyaTranslation(surahID: SurahID): Flow<List<AyaRepoModel>> {
        TODO("Not yet implemented")
    }
}