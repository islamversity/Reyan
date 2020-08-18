package com.islamversity.domain.repo.surah

import com.islamversity.core.Mapper
import com.islamversity.core.listMap
import com.islamversity.db.datasource.SurahLocalDataSource
import com.islamversity.db.model.Surah
import com.islamversity.domain.model.Calligraphy
import com.islamversity.domain.model.SurahRepoModel
import com.islamversity.domain.model.toDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


interface SurahListRepo {
    fun getAllSurah(calligraphy: Calligraphy): Flow<List<SurahRepoModel>>
}

class SurahListRepoImpl(
    private val surahDataSource: SurahLocalDataSource,
    private val surahMapper: Mapper<Surah, SurahRepoModel>
) : SurahListRepo {

    override fun getAllSurah(calligraphy: Calligraphy): Flow<List<SurahRepoModel>> =
        surahDataSource.observeAllSurahs(calligraphy.toDB())
            .map {
                surahMapper.listMap(it)
            }
}
