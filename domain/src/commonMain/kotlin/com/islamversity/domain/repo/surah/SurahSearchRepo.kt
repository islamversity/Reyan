package com.islamversity.domain.repo.surah

import com.islamversity.core.Mapper
import com.islamversity.core.listMap
import com.islamversity.core.mapListWith
import com.islamversity.db.datasource.SurahLocalDataSource
import com.islamversity.db.model.Surah
import com.islamversity.db.model.SurahWithTwoName
import com.islamversity.domain.model.Calligraphy
import com.islamversity.domain.model.CalligraphyId
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SurahSearchRepo {

    fun search(query: String, arabicCalligraphy: CalligraphyId, mainCalligraphy: CalligraphyId): Flow<List<SurahRepoModel>>
}

class SurahSearchRepoImpl(
    private val dataSource: SurahLocalDataSource,
    private val mapper: Mapper<SurahWithTwoName, SurahRepoModel>
) : SurahSearchRepo {

    override fun search(query: String, arabicCalligraphy: CalligraphyId, mainCalligraphy: CalligraphyId): Flow<List<SurahRepoModel>> =
        dataSource.findSurahByName(
            nameQuery = query,
            arabicCalligraphy = arabicCalligraphy.toEntity(),
            mainCalligraphy = mainCalligraphy.toEntity(),
        ).mapListWith(mapper)
}
