package com.islamversity.db.datasource

import com.islamversity.db.*
import com.islamversity.db.model.*
import com.islamversity.db.model.Aya
import com.islamversity.db.model.Calligraphy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    fun observeAllAyasWithTranslationForSora(
        surahId: SurahId,
        calligraphy: CalligraphyId,
        translationCalligraphy: CalligraphyId,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<List<Aya>>

    fun observeAllAyasWith2TranslationForSora(
        surahId: SurahId,
        calligraphy: CalligraphyId,
        translationCalligraphy: CalligraphyId,
        translation2Calligraphy: CalligraphyId,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<List<Aya>>

    fun getAyaWithId(
        entityId: AyaId,
        calligraphy: Calligraphy,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<Aya?>

    fun getJuz(surahNameCalligraphy: CalligraphyId, context: CoroutineContext = Dispatchers.Default): Flow<List<JuzEntity>>
}

const val NUMBER_OF_HIZB_IN_EACH_JUZ_WITH_START_AND_END = 16
const val NUMBER_OF_START_ENDING_ITEMS = 2

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
        ayaQueries.getAllAyaBySoraId(calligraphy, surahId){rowIndex, id, orderIndex, surahId, ayaText, sajdahType, juzOrderIndex, hizbQuarterOrderIndex ->
            ayaMapper(rowIndex,id, orderIndex, surahId, ayaText, null, null, sajdahType, juzOrderIndex, hizbQuarterOrderIndex)
        }
            .asFlow()
            .mapToList(context)

    override fun observeAllAyasWithTranslationForSora(
        surahId: SurahId,
        calligraphy: CalligraphyId,
        translationCalligraphy: CalligraphyId,
        context: CoroutineContext
    ): Flow<List<Aya>> =
        ayaQueries.getAllAyaWithTranslationBySoraId(calligraphy,translationCalligraphy, surahId){rowIndex, id, orderIndex, surahId, ayaText, translation, sajdahType, juzOrderIndex, hizbQuarterOrderIndex ->
            ayaMapper(rowIndex,id, orderIndex, surahId, ayaText, translation, null, sajdahType, juzOrderIndex, hizbQuarterOrderIndex)
        }
            .asFlow()
            .mapToList(context)

    override fun observeAllAyasWith2TranslationForSora(
        surahId: SurahId,
        calligraphy: CalligraphyId,
        translationCalligraphy: CalligraphyId,
        translation2Calligraphy: CalligraphyId,
        context: CoroutineContext
    ): Flow<List<Aya>> =
        ayaQueries.getAllAyaWith2TranslationBySoraId(calligraphy,translationCalligraphy, translation2Calligraphy,  surahId){rowIndex, id, orderIndex, surahId, ayaText, translation, translation2, sajdahType, juzOrderIndex, hizbQuarterOrderIndex ->
            ayaMapper(rowIndex,id, orderIndex, surahId, ayaText, translation, translation2, sajdahType, juzOrderIndex, hizbQuarterOrderIndex)
        }
            .asFlow()
            .mapToList(context)

    override fun getAyaWithId(
        entityId: AyaId,
        calligraphy: Calligraphy,
        context: CoroutineContext
    ): Flow<Aya?> =
        ayaQueries.getAyaById(calligraphy, entityId){rowIndex, id, orderIndex, surahId, ayaText, sajdahType, juzOrderIndex, hizbQuarterOrderIndex ->
            ayaMapper(rowIndex,id, orderIndex, surahId, ayaText, null, null, sajdahType, juzOrderIndex, hizbQuarterOrderIndex)
        }
            .asFlow()
            .mapToOneOrNull(context)

    override fun getJuz(surahNameCalligraphy: CalligraphyId, context: CoroutineContext): Flow<List<JuzEntity>> =
        ayaQueries.getJuzs(surahNameCalligraphy)
            .asFlow()
            .mapToList(context)
            .map { rows ->
                val juzs = mutableListOf<JuzEntity>()

                for (index in rows.indices step NUMBER_OF_HIZB_IN_EACH_JUZ_WITH_START_AND_END) {
                    val startingJuz = rows[index]

                    val hizbs = mutableListOf<HizbEntity>()
                    var lastHizbIndex = 0
                    for (hizbIndex in index until rows.size step NUMBER_OF_START_ENDING_ITEMS) {
                        val startingHizb = rows[hizbIndex]
                        val endingHizb = rows[hizbIndex + 1]

                        if (startingHizb.juzOrderIndex != startingJuz.juzOrderIndex) {
                            break
                        }

                        hizbs.add(
                            HizbEntity(
                                startingHizb.id,
                                startingHizb.orderIndex,
                                startingHizb.surahId,
                                startingHizb.content!!,

                                endingHizb.id,
                                endingHizb.orderIndex,
                                endingHizb.surahId,
                                endingHizb.content!!,

                                startingHizb.juzOrderIndex,
                                startingHizb.hizbQuarterOrderIndex,
                            )
                        )
                        lastHizbIndex = hizbIndex
                    }
                    val endingJuz = rows[lastHizbIndex + 1]

                    juzs.add(
                        JuzEntity(
                            startingJuz.id,
                            startingJuz.orderIndex,
                            startingJuz.surahId,
                            startingJuz.content!!,

                            endingJuz.id,
                            endingJuz.orderIndex,
                            endingJuz.surahId,
                            endingJuz.content!!,

                            startingJuz.juzOrderIndex,
                            hizbs
                        )
                    )
                }

                juzs
            }

    private fun insertContent(ayaContent: No_rowId_aya_content) {
        ayaContentQueries.insertAyaContent(ayaContent.id, ayaContent.ayaId, ayaContent.calligraphy, ayaContent.content)
    }

    private val ayaMapper: (
        rowIndex: Long,
        id: AyaId,
        orderIndex: AyaOrderId,
        surahId: SurahId,
        ayaText: String?,
        ayaTranslation1 : String?,
        ayaTranslation2 : String?,
        sajdahTypeFlag: SajdahTypeFlag,
        juzOrderIndex: Juz,
        hizbOrderIndex: HizbQuarter
    ) -> Aya = { rowIndex, id, orderIndex, surahId, ayaText, translation1, translation2,sajdahType, juzOrderIndex, hizbOrderIndex ->
        Aya(
            rowIndex,
            id,
            orderIndex,
            surahId,
            ayaText!!,
            translation1,
            translation2,
            sajdahType,
            juzOrderIndex,
            hizbOrderIndex
        )
    }

}