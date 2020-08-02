package com.islamversity.db.datasource

import com.islamversity.db.*
import com.islamversity.db.model.*
import com.islamversity.db.model.Aya
import com.islamversity.db.model.Calligraphy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

interface AyaLocalDataSource {
    suspend fun insertAya(
        aya: AyaWithFullContent,
        context: CoroutineContext = Dispatchers.Default
    )

    suspend fun insertAyas(
        ayas: List<AyaWithFullContent>,
        context: CoroutineContext = Dispatchers.Default
    )

    fun observeAllAyasForSora(
        soraId: SoraId,
        calligraphy: Calligraphy,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<List<Aya>>

    fun observeAllAyasForSora(
        soraOrder: Long,
        calligraphy: Calligraphy,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<List<Aya>>

    fun getAyaWithId(
        entityId: AyaId,
        calligraphy: Calligraphy,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<Aya?>
}

class AyaLocalDataSourceImpl(
    private val ayaQueries: AyaQueries,
    private val ayaContentQueries: AyaContentQueries
) : AyaLocalDataSource {

    override suspend fun insertAya(
        aya: AyaWithFullContent,
        context: CoroutineContext
    ) {
        withContext(context) {
            //order is important
            ayaQueries.insertAya(aya.id, aya.order, aya.soraId)
            insertContent(aya.content)
        }
    }

    override suspend fun insertAyas(
        ayas: List<AyaWithFullContent>,
        context: CoroutineContext
    ) {
        withContext(context) {
            ayaQueries.transaction {
                ayas.forEach {
                    //order is important
                    ayaQueries.insertAya(it.id, it.order, it.soraId)
                    insertContent(it.content)
                }
            }
        }
    }

    override fun observeAllAyasForSora(
        soraId: SoraId,
        calligraphy: Calligraphy,
        context: CoroutineContext
    ): Flow<List<Aya>> =
        ayaQueries.getAllAyaBySoraId(calligraphy, soraId) { index, id, order, content ->
            Aya.WithSoraId(index, id, order, content!!, soraId)
        }
            .asFlow()
            .mapToList(context)

    override fun observeAllAyasForSora(
        soraOrder: Long,
        calligraphy: Calligraphy,
        context: CoroutineContext
    ): Flow<List<Aya>> =
        ayaQueries.getAllAyaBySoraOrder(calligraphy, soraOrder) { index, id, order, content ->
            Aya.WithSoraOrderCalligraphy(index, id, order, content!!, soraOrder, calligraphy)
        }
            .asFlow()
            .mapToList()

    override fun getAyaWithId(
        entityId: AyaId,
        calligraphy: Calligraphy,
        context: CoroutineContext
    ): Flow<Aya?> =
        ayaQueries.getAyaById(calligraphy, entityId) { index, id, order, soraId, aya ->
            Aya.WithSoraId(index, id, order, aya!!, soraId)
        }
            .asFlow()
            .mapToOneOrNull(context)

    private fun insertContent(ayaContent: No_rowId_aya_content) {
        ayaContentQueries.insertAyaContent(ayaContent.id, ayaContent.ayaId, ayaContent.calligraphy, ayaContent.content)
    }

}