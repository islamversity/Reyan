package com.islamversity.db.datasource

import com.islamversity.db.*
import com.islamversity.db.model.*
import com.islamversity.db.model.Calligraphy
import com.islamversity.db.model.Surah
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

interface SurahLocalDataSource {
    suspend fun insertSurah(
        surah: SurahWithFullName,
        context: CoroutineContext = Dispatchers.Default
    )

    suspend fun insertSurah(
        surahs: List<SurahWithFullName>,
        context: CoroutineContext = Dispatchers.Default
    )

    fun observeAllSurahs(
        calligraphy: CalligraphyId,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<List<Surah>>

    fun getSurahWithId(
        entityId: SurahId,
        calligraphy: CalligraphyId,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<Surah?>

    fun getSurahWithOrderAndCalligraphy(
        order: SurahOrderId,
        calligraphy: CalligraphyId,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<Surah?>

    fun findSurahByName(
        nameQuery: String,
        calligraphy: CalligraphyId,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<List<Surah>>
}

class SurahLocalDataSourceImpl(
    private val surahQueries: SurahQueries,
    private val nameQueries: NameQueries
) : SurahLocalDataSource {

    override suspend fun insertSurah(surah: SurahWithFullName, context: CoroutineContext) {
        withContext(context) {
            //order is important
            surahQueries.insertSurah(
                surah.id,
                surah.order,
                surah.revealTypeId,
                surah.revealTypeFlag,
                surah.bismillahTypeFlag
            )
            insertName(surah.name)
        }
    }

    override suspend fun insertSurah(
        surahs: List<SurahWithFullName>,
        context: CoroutineContext
    ) {
        withContext(context) {
            surahQueries.transaction {
                surahs.forEach {
                    //order is important
                    surahQueries.insertSurah(
                        it.id,
                        it.order,
                        it.revealTypeId,
                        it.revealTypeFlag,
                        it.bismillahTypeFlag
                    )
                    insertName(it.name)
                }
            }
        }
    }

    override fun observeAllSurahs(
        calligraphy: CalligraphyId,
        context: CoroutineContext
    ): Flow<List<Surah>> =
        surahQueries.getAllSurah(calligraphy, surahMapper)
            .asFlow()
            .mapToList(context)

    override fun getSurahWithId(
        entityId: SurahId,
        calligraphy: CalligraphyId,
        context: CoroutineContext
    ): Flow<Surah?> =
        surahQueries.getSurahWithId(calligraphy, entityId, surahMapper)
            .asFlow()
            .mapToOneOrNull(context)

    override fun getSurahWithOrderAndCalligraphy(
        order: SurahOrderId,
        calligraphy: CalligraphyId,
        context: CoroutineContext
    ): Flow<Surah?> =
        surahQueries.getSurahWithOrder(calligraphy, order, surahMapper)
            .asFlow()
            .mapToOneOrNull(context)

    override fun findSurahByName(
        nameQuery: String,
        calligraphy: CalligraphyId,
        context: CoroutineContext
    ): Flow<List<Surah>> =
        surahQueries.findSurahByName(calligraphy, nameQuery, surahMapper)
            .asFlow()
            .mapToList(context)

    private fun insertName(surahName: No_rowId_name) {
        nameQueries.insertName(
            surahName.id,
            surahName.rowId,
            surahName.calligraphy,
            surahName.content
        )
    }

    private val surahMapper: (
        index: Long,
        id: SurahId,
        orderIndex: SurahOrderId,
        name: String?,
        revealType: String?,
        revealFlag: RevealTypeFlag,
        bismillahFlag: BismillahTypeFlag,
        ayaCount: AyaOrderId
    ) -> Surah = { index, id, orderIndex, name, revealType, revealFlag, bismillahFlag, ayaCount ->
        Surah(
            index,
            id,
            orderIndex,
            name!!,
            RevealType.fromFLag(revealFlag, revealType!!),
            bismillahFlag,
            ayaCount.order
        )
    }
}
