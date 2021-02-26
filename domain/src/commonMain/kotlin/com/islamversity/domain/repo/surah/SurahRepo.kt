package com.islamversity.domain.repo.surah

import com.islamversity.core.*
import com.islamversity.db.datasource.SettingsDataSource
import com.islamversity.db.datasource.SurahLocalDataSource
import com.islamversity.db.model.SurahWithTwoName
import com.islamversity.domain.model.CalligraphyId
import com.islamversity.domain.model.surah.SurahID
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.model.surah.SurahStateRepoModel
import com.islamversity.domain.model.surah.toEntity
import com.islamversity.domain.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val KEY_SURAH_STATE_NAME = "KEY_SURAH_STATE_NAME"
private const val KEY_SURAH_STATE_ID = "KEY_SURAH_STATE_ID"
private const val KEY_SURAH_STATE_AYA_ORDER = "KEY_SURAH_STATE_AYA_ORDER"

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

    fun saveSurahState(state: SurahStateRepoModel): Flow<Boolean>
    fun getSurahState(): Flow<SurahStateRepoModel?>
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

    override fun saveSurahState(state: SurahStateRepoModel) = flow {
        Logger.log("SurahState save, surahName: ${state.surahName}")
        settingDataSource.put(KEY_SURAH_STATE_NAME,state.surahName)
        settingDataSource.put(KEY_SURAH_STATE_ID,state.surahID)
        settingDataSource.put(KEY_SURAH_STATE_AYA_ORDER,state.startingAyaOrder.toString())
        emit(true)
    }

    override fun getSurahState(): Flow<SurahStateRepoModel?> = flow {

        val surahName = settingDataSource.get(KEY_SURAH_STATE_NAME)
        val surahID= settingDataSource.get(KEY_SURAH_STATE_ID)
        val startingAyaOrder = settingDataSource.get(KEY_SURAH_STATE_AYA_ORDER)

        Logger.log("SurahState get, surahName: $surahName")

        if (surahID.isNullOrBlank()||
            surahName.isNullOrBlank()||
            startingAyaOrder.isNullOrBlank()){
            emit(null)
        }else{
            emit(SurahStateRepoModel(surahName,surahID,startingAyaOrder.toLong()))
        }

    }
}
