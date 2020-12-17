package com.islamversity.domain.repo.surah

import com.islamversity.core.Mapper
import com.islamversity.core.mapListWith
import com.islamversity.core.mapWithNullable
import com.islamversity.db.datasource.SurahLocalDataSource
import com.islamversity.db.model.SurahWithTwoName
import com.islamversity.domain.model.CalligraphyId
import com.islamversity.domain.model.surah.SurahID
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.model.surah.toEntity
import com.islamversity.domain.model.toEntity
import kotlinx.coroutines.flow.Flow


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
}

class SurahRepoImpl(
    private val dataSource: SurahLocalDataSource,
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
}
