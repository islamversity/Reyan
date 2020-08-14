package com.islamversity.db.datasource

import com.islamversity.db.model.SoraEntityModel
import kotlinx.coroutines.flow.Flow

interface SoraLocalDataSource {
    fun searchSora(query: String): Flow<List<SoraEntityModel>?>
}
class SoraLocalDataSourceImpl : SoraLocalDataSource {
    override fun searchSora(query: String): Flow<List<SoraEntityModel>?> {
        TODO("Not yet implemented")
    }


}