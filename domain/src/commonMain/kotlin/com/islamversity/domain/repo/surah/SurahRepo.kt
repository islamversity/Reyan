package com.islamversity.domain.repo.surah

import com.islamversity.core.*
import com.islamversity.db.datasource.SettingsDataSource
import com.islamversity.db.datasource.SurahLocalDataSource
import com.islamversity.db.model.SurahWithTwoName
import com.islamversity.domain.model.CalligraphyId
import com.islamversity.domain.model.surah.SurahID
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.model.surah.ReadingBookmarkRepoModel
import com.islamversity.domain.model.surah.toEntity
import com.islamversity.domain.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val KEY_SURAH_SCROLL = "KEY_SURAH_SCROLL"

interface SurahRepo {
    fun getAllSurah(arabicCalligraphy: CalligraphyId, mainCalligraphy: CalligraphyId): Flow<List<SurahRepoModel>>

    fun getAll(
        ids: List<SurahID>,
        arabicCalligraphy: CalligraphyId,
        mainCalligraphy: CalligraphyId
    ): Flow<List<SurahRepoModel>>

    fun getSurah(
        surahId: SurahID,
        arabicCalligraphy: CalligraphyId,
        mainCalligraphy: CalligraphyId
    ): Flow<SurahRepoModel?>

    suspend fun saveBookmarkAya(state: ReadingBookmarkRepoModel)
    fun getBookmarkAya(): Flow<ReadingBookmarkRepoModel?>
}

class SurahRepoImpl(
    private val dataSource: SurahLocalDataSource,
    private val settingDataSource: SettingsDataSource,
    private val twoNameMapper: Mapper<SurahWithTwoName, SurahRepoModel>,
) : SurahRepo {

    override fun getAllSurah(
        arabicCalligraphy: CalligraphyId,
        mainCalligraphy: CalligraphyId
    ): Flow<List<SurahRepoModel>> =
        dataSource.observeAllSurahs(arabicCalligraphy.toEntity(), mainCalligraphy.toEntity())
            .mapListWith(twoNameMapper)

    override fun getAll(
        ids: List<SurahID>,
        arabicCalligraphy: CalligraphyId,
        mainCalligraphy: CalligraphyId
    ): Flow<List<SurahRepoModel>> =
        dataSource.observeAllSurahs(ids.map { it.toEntity() }, arabicCalligraphy.toEntity(), mainCalligraphy.toEntity())
            .mapListWith(twoNameMapper)

    override fun getSurah(
        surahId: SurahID,
        arabicCalligraphy: CalligraphyId,
        mainCalligraphy: CalligraphyId
    ): Flow<SurahRepoModel?> =
        dataSource.getSurahWithId(surahId.toEntity(), arabicCalligraphy.toEntity(), mainCalligraphy.toEntity())
            .mapWithNullable(twoNameMapper)

    override suspend fun saveBookmarkAya(state: ReadingBookmarkRepoModel) {
        Logger.log("SurahState save, surahName: $state")
        settingDataSource.put(KEY_SURAH_SCROLL,Json.encodeToString(state))
    }

    override fun getBookmarkAya(): Flow<ReadingBookmarkRepoModel?> =
        settingDataSource.observeKey(KEY_SURAH_SCROLL)
            .map {
                it?.let {
                    Json.decodeFromString(it)
                }
            }
}
