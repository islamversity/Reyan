package com.islamversity.domain.repo.surah

import com.islamversity.db.datasource.CalligraphyLocalDataSource
import com.islamversity.domain.model.CalligraphyId
import com.islamversity.domain.model.surah.SurahID
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.repo.SettingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.flatMapMerge

interface GetSurahUsecase {
    fun getSurahs(): Flow<List<SurahRepoModel>>

    fun getSurah(id: SurahID): Flow<SurahRepoModel?>
}

class GetSurahUsecaseImpl(
    private val surahRepo: SurahRepo,
    private val settingRepo: SettingRepo,
    private val calligraphyDS: CalligraphyLocalDataSource,
) : GetSurahUsecase {
    override fun getSurahs() =
        calligraphyDS.getArabicSurahCalligraphy().combineTransform(settingRepo.getSecondarySurahNameCalligraphy()) { arabic, main ->
            emit(arabic to main)
        }
            .flatMapMerge {
                surahRepo.getAllSurah(arabicCalligraphy = CalligraphyId(it.first.id.id), mainCalligraphy = it.second.id)
            }

    override fun getSurah(id: SurahID): Flow<SurahRepoModel?> =
        calligraphyDS.getArabicSurahCalligraphy().combineTransform(settingRepo.getSecondarySurahNameCalligraphy()) { arabic, main ->
            emit(arabic to main)
        }
            .flatMapMerge {
                surahRepo.getSurah(id, arabicCalligraphy = CalligraphyId(it.first.id.id), mainCalligraphy = it.second.id)
            }
}
