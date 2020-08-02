package com.islamversity.db.datasource

import com.islamversity.db.*
import com.islamversity.db.model.*
import com.islamversity.db.model.Calligraphy
import com.islamversity.db.model.Sora
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

interface SoraLocalDataSource {
    suspend fun insertSora(
        sora: SoraWithFullName,
        context: CoroutineContext = Dispatchers.Default
    )

    suspend fun insertSoras(
        soras: List<SoraWithFullName>,
        context: CoroutineContext = Dispatchers.Default
    )

    fun observeAllSoras(
        calligraphy: Calligraphy,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<List<Sora>>

    fun getSoraWithId(
        entityId: SoraId,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<Sora?>

    fun getSoraWithOrderAndCalligraphy(
        order: Long,
        calligraphy: Calligraphy,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<Sora?>
}

class SoraLocalDataSourceImpl(
    private val soraQueries: SoraQueries,
    private val nameQueries: NameQueries
) : SoraLocalDataSource {

    override suspend fun insertSora(sora: SoraWithFullName, context: CoroutineContext) {
        withContext(context) {
            //order is important
            soraQueries.insertSora(sora.id, sora.order)
            insertName(sora.name)
        }
    }

    override suspend fun insertSoras(
        soras: List<SoraWithFullName>,
        context: CoroutineContext
    ) {
        withContext(context) {
            soraQueries.transaction {
                soras.forEach {
                    //order is important
                    soraQueries.insertSora(it.id, it.order)
                    insertName(it.name)
                }
            }
        }
    }

    override fun observeAllSoras(
        calligraphy: Calligraphy,
        context: CoroutineContext
    ): Flow<List<Sora>> =
        soraQueries.getAllSoras(calligraphy) { index, id, order, name ->
            Sora(index, id, order, name!!)
        }
            .asFlow()
            .mapToList(context)

    override fun getSoraWithId(entityId: SoraId, context: CoroutineContext): Flow<Sora?> =
        soraQueries.getSoraWithId(entityId) { index, id, order, name ->
            Sora(index, id, order, name!!)
        }
            .asFlow()
            .mapToOneOrNull(context)

    override fun getSoraWithOrderAndCalligraphy(order: Long, calligraphy: Calligraphy, context: CoroutineContext): Flow<Sora?> =
        soraQueries.getSoraWithOrder(calligraphy, order) { index, id, order, name ->
            Sora(index, id, order, name!!)
        }
            .asFlow()
            .mapToOneOrNull(context)

    private fun insertName(soraName: No_rowId_name) {
        nameQueries.insertName(soraName.id, soraName.parentId, soraName.calligraphy, soraName.content)
    }
}