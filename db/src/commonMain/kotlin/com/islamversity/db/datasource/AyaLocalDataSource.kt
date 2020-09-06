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
        surahId: SurahId,
        calligraphy: CalligraphyId,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<List<Aya>>

    fun observeAllAyasForSora(
        soraOrder: SurahOrderId,
        calligraphy: CalligraphyId,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<List<Aya>>

    fun getAyaWithId(
        entityId: AyaId,
        calligraphy: Calligraphy,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<Aya?>

    fun getJuz(context: CoroutineContext = Dispatchers.Default): Flow<List<JuzEntity>>

    fun getHizb(context: CoroutineContext = Dispatchers.Default): Flow<List<HizbEntity>>
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
            ayaQueries.insertAya(aya.id, aya.order, aya.surahId, aya.sajdahId, aya.sajdahTypeFlag, aya.juz, aya.hizb)
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
                    ayaQueries.insertAya(it.id, it.order, it.surahId, it.sajdahId, it.sajdahTypeFlag, it.juz, it.hizb)
                    insertContent(it.content)
                }
            }
        }
    }

    override fun observeAllAyasForSora(
        surahId: SurahId,
        calligraphy: CalligraphyId,
        context: CoroutineContext
    ): Flow<List<Aya>> =
        ayaQueries.getAllAyaBySoraId(calligraphy, surahId, ayaMapper)
            .asFlow()
            .mapToList(context)

    override fun observeAllAyasForSora(
        soraOrder: SurahOrderId,
        calligraphy: CalligraphyId,
        context: CoroutineContext
    ): Flow<List<Aya>> =
        ayaQueries.getAllAyaBySoraOrder(calligraphy, soraOrder, ayaMapper)
            .asFlow()
            .mapToList()

    override fun getAyaWithId(
        entityId: AyaId,
        calligraphy: Calligraphy,
        context: CoroutineContext
    ): Flow<Aya?> =
        ayaQueries.getAyaById(calligraphy, entityId, ayaMapper)
            .asFlow()
            .mapToOneOrNull(context)

    override fun getJuz(context: CoroutineContext): Flow<List<JuzEntity>> =
        ayaQueries.getAllJuz { juzOrderIndex, id, orderIndex, surahId, hizbOrderIndex ->
            JuzEntity(id, surahId, juzOrderIndex, hizbOrderIndex)
        }
            .asFlow()
            .mapToList(context)

    override fun getHizb(context: CoroutineContext): Flow<List<HizbEntity>> =
        ayaQueries.getAllHizb { juzOrderIndex, id, orderIndex, surahId, hizbOrderIndex ->
            HizbEntity(id, surahId, hizbOrderIndex, juzOrderIndex)
        }
            .asFlow()
            .mapToList(context)

    private fun insertContent(ayaContent: No_rowId_aya_content) {
        ayaContentQueries.insertAyaContent(ayaContent.id, ayaContent.ayaId, ayaContent.calligraphy, ayaContent.content)
    }

    private val ayaMapper: (
        rowIndex: Long,
        id: AyaId,
        orderIndex: AyaOrderId,
        surahId: SurahId,
        ayaText: String?,
        sajdahText: String?,
        sajdahTypeFlag: SajdahTypeFlag,
        juzOrderIndex: Juz,
        hizbOrderIndex: HizbQuarter
    ) -> Aya = { rowIndex, id, orderIndex, surahId, ayaText, sajdahText, sajdahType, juzOrderIndex, hizbOrderIndex ->
        Aya(
            rowIndex,
            id,
            orderIndex,
            surahId,
            ayaText!!,
            SajdahType(sajdahType, sajdahText!!),
            juzOrderIndex,
            hizbOrderIndex
        )
    }

}