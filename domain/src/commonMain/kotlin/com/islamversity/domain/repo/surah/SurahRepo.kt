package com.islamversity.domain.repo.surah

import com.islamversity.core.Mapper
import com.islamversity.core.listMap
import com.islamversity.core.mapWith
import com.islamversity.core.mapWithNullable
import com.islamversity.db.datasource.SurahLocalDataSource
import com.islamversity.db.model.Surah
import com.islamversity.domain.model.Calligraphy
import com.islamversity.domain.model.CalligraphyId
import com.islamversity.domain.model.surah.SurahID
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.model.surah.toEntity
import com.islamversity.domain.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


interface SurahRepo {
    fun getAllSurah(calligraphy: CalligraphyId): Flow<List<SurahRepoModel>>

    fun getSurah(surahId : SurahID, calligraphy: CalligraphyId) : Flow<SurahRepoModel?>
}

class SurahRepoImpl(
    private val dataSource: SurahLocalDataSource,
    private val mapper: Mapper<Surah, SurahRepoModel>
) : SurahRepo {

    override fun getAllSurah(calligraphy: CalligraphyId): Flow<List<SurahRepoModel>> =
        dataSource.observeAllSurahs(calligraphy.toEntity())
            .map {
                mapper.listMap(it)
            }

    override fun getSurah(surahId: SurahID, calligraphy: CalligraphyId): Flow<SurahRepoModel?> =
        dataSource.getSurahWithId(surahId.toEntity(), calligraphy.toEntity())
            .mapWithNullable(mapper)
}
