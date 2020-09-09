package com.islamversity.domain.repo.surah

import com.islamversity.core.Mapper
import com.islamversity.core.listMap
import com.islamversity.db.datasource.SurahLocalDataSource
import com.islamversity.db.model.Surah
import com.islamversity.domain.model.Calligraphy
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SurahSearchRepo {

    fun search(query: String, calligraphy: Calligraphy): Flow<List<SurahRepoModel>>
}

class SurahSearchRepoImpl(
    private val dataSource: SurahLocalDataSource,
    private val mapper: Mapper<Surah, SurahRepoModel>
) : SurahSearchRepo {

    override fun search(query: String, calligraphy: Calligraphy): Flow<List<SurahRepoModel>> =
        dataSource.findSurahByName(
            nameQuery = query,
            calligraphy = calligraphy.id.toEntity()
        )
            .map {
                mapper.listMap(it)
            }
}
