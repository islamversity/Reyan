package com.islamversity.domain.repo

import com.islamversity.core.Mapper
import com.islamversity.core.listMap
import com.islamversity.db.datasource.SoraLocalDataSource
import com.islamversity.db.model.Calligraphy
import com.islamversity.db.model.Sora
import com.islamversity.domain.model.sora.SoraRepoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SoraSearchRepo {

    fun search(query: String): Flow<List<SoraRepoModel>>
}

class SoraSearchRepoImpl(
    private val dataSource: SoraLocalDataSource,
    private val mapper: Mapper<Sora, SoraRepoModel>
) : SoraSearchRepo {

    override fun search(query: String): Flow<List<SoraRepoModel>> =
        dataSource.observeAllSoras(Calligraphy(TODO()))
            .map{
                mapper.listMap(it)
            }
}
