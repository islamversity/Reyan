package com.islamversity.domain.repo.surah

import com.islamversity.core.Logger
import com.islamversity.db.datasource.CalligraphyLocalDataSource
import com.islamversity.domain.model.CalligraphyId
import com.islamversity.domain.model.surah.SurahID
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.repo.SettingRepo
import kotlinx.coroutines.flow.*

interface GetSurahUsecase {
    fun getSurahs(): Flow<List<SurahRepoModel>>

    fun getSurah(id: SurahID): Flow<SurahRepoModel?>

    fun getAll(ids: List<SurahID>): Flow<List<SurahRepoModel>>
}

class GetSurahUsecaseImpl(
    private val surahRepo: SurahRepo,
    private val settingRepo: SettingRepo,
    private val calligraphyDS: CalligraphyLocalDataSource,
) : GetSurahUsecase {
    override fun getSurahs() =
        calligraphyDS.getArabicSurahCalligraphy()
            .combineTransform(settingRepo.getSecondarySurahNameCalligraphy()) { arabic, main ->
                emit(arabic to main)
            }
            .onEach {
                Logger.log {
                    "GetSurah" + it.first.toString() + " - " + it.second.toString()
                }
            }
            .flatMapLatest {
                surahRepo.getAllSurah(arabicCalligraphy = CalligraphyId(it.first.id.id), mainCalligraphy = it.second.id)
            }

    override fun getSurah(id: SurahID): Flow<SurahRepoModel?> =
        calligraphyDS.getArabicSurahCalligraphy()
            .combineTransform(settingRepo.getSecondarySurahNameCalligraphy()) { arabic, main ->
                emit(arabic to main)
            }
            .flatMapLatest {
                surahRepo.getSurah(
                    id,
                    arabicCalligraphy = CalligraphyId(it.first.id.id),
                    mainCalligraphy = it.second.id
                )
            }

    override fun getAll(ids: List<SurahID>): Flow<List<SurahRepoModel>> =
        calligraphyDS.getArabicSurahCalligraphy()
            .combineTransform(settingRepo.getSecondarySurahNameCalligraphy()) { arabic, main ->
                emit(arabic to main)
            }
            .flatMapLatest {
                surahRepo.getAll(
                    ids = ids,
                    arabicCalligraphy = CalligraphyId(it.first.id.id),
                    mainCalligraphy = it.second.id
                )
            }
}
