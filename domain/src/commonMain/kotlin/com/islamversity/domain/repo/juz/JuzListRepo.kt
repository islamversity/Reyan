package com.islamversity.domain.repo.juz

import com.islamversity.core.Mapper
import com.islamversity.core.listMap
import com.islamversity.core.mapListWith
import com.islamversity.db.datasource.AyaLocalDataSource
import com.islamversity.db.model.JuzEntity
import com.islamversity.domain.model.CalligraphyId
import com.islamversity.domain.model.JuzRepoModel
import com.islamversity.domain.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface JuzListRepo {
    fun getAllJuz(calligraphyId: CalligraphyId): Flow<List<JuzRepoModel>>
}

class JuzListRepoImpl(
    private val ayaDataSource: AyaLocalDataSource,
    private val juzMapper: Mapper<JuzEntity, JuzRepoModel>
) : JuzListRepo {

    override fun getAllJuz(calligraphyId: CalligraphyId): Flow<List<JuzRepoModel>> =
        ayaDataSource.getJuz(calligraphyId.toEntity()).mapListWith(juzMapper)
}
