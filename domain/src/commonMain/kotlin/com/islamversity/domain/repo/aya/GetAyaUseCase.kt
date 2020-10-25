package com.islamversity.domain.repo.aya

import com.islamversity.domain.model.SettingsCalligraphy
import com.islamversity.domain.model.aya.AyaRepoModel
import com.islamversity.domain.model.surah.SurahID
import com.islamversity.domain.repo.CalligraphyRepo
import com.islamversity.domain.repo.SettingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf

interface GetAyaUseCase {
    fun observeAyaMain(surahID: SurahID): Flow<List<AyaRepoModel>>
}

class GetAyaUseCaseImpl(
    private val ayaListRepo: AyaListRepo,
    private val settingRepo: SettingRepo,
    private val calligraphyRepo: CalligraphyRepo,
) : GetAyaUseCase {
    override fun observeAyaMain(surahID: SurahID): Flow<List<AyaRepoModel>> =
        combine(
            settingRepo.getFirstAyaTranslationCalligraphy(),
            settingRepo.getSecondAyaTranslationCalligraphy(),
            calligraphyRepo.getMainAyaCalligraphy(),
        ) { first, second, main ->
            val firstAndSecond = first to second
            firstAndSecond to main
        }
            .flatMapLatest { firstAndSecondAndMain ->
                val firstAndSecond = firstAndSecondAndMain.first
                val main = firstAndSecondAndMain.second

                if (firstAndSecond.first is SettingsCalligraphy.Selected && firstAndSecond.second is SettingsCalligraphy.Selected) {
                    val firstCall = firstAndSecond.first as SettingsCalligraphy.Selected
                    val secondCall = firstAndSecond.second as SettingsCalligraphy.Selected
                    ayaListRepo.observeWith2TranslationAllAyas(surahID, main.id, firstCall.cal.id, secondCall.cal.id)

                } else if (firstAndSecond.first is SettingsCalligraphy.Selected) {
                    val firstCall = firstAndSecond.first as SettingsCalligraphy.Selected
                    ayaListRepo.observeWithTranslationAllAyas(surahID, main.id, firstCall.cal.id)

                } else if (firstAndSecond.second is SettingsCalligraphy.Selected) {
                    val secondCall = firstAndSecond.second as SettingsCalligraphy.Selected
                    ayaListRepo.observeWithTranslationAllAyas(surahID, main.id, secondCall.cal.id)

                } else {
                    ayaListRepo.observeAllAyas(surahID, main.id)
                }
            }
}