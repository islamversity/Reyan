package com.islamversity.domain.repo

import com.islamversity.core.Mapper
import com.islamversity.core.listMap
import com.islamversity.core.suspendToFlow
import com.islamversity.db.datasource.SoraLocalDataSource
import com.islamversity.db.model.SoraEntityModel
import com.islamversity.domain.model.sora.SoraRepoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SoraSearchRepo {

    fun search(query: String): Flow<List<SoraRepoModel>>
}

class SoraSearchRepoImpl(
    private val dataSource: SoraLocalDataSource,
    private val mapper: Mapper<SoraEntityModel, SoraRepoModel>
) : SoraSearchRepo {

    override fun search(query: String): Flow<List<SoraRepoModel>> =

        dataSource.searchSora(query)
            .map{
                mapper.listMap(it)
            }
}
