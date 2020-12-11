package com.islamversity.domain.usecase

import co.touchlab.stately.concurrency.AtomicReference
import co.touchlab.stately.concurrency.value
import com.autodesk.coroutineworker.CoroutineWorker
import com.islamversity.core.Logger
import com.islamversity.core.Severity
import com.islamversity.db.AyaContentQueries
import com.islamversity.db.AyaQueries
import com.islamversity.db.Calligraphy
import com.islamversity.db.CalligraphyQueries
import com.islamversity.db.Main
import com.islamversity.db.NameQueries
import com.islamversity.db.SurahQueries
import com.islamversity.db.model.AyaContentId
import com.islamversity.db.model.AyaId
import com.islamversity.db.model.AyaOrderId
import com.islamversity.db.model.BismillahTypeFlag
import com.islamversity.db.model.CalligraphyId
import com.islamversity.db.model.CalligraphyName
import com.islamversity.db.model.HizbQuarter
import com.islamversity.db.model.Juz
import com.islamversity.db.model.LanguageCode
import com.islamversity.db.model.NameId
import com.islamversity.db.model.RawId
import com.islamversity.db.model.RevealTypeFlag
import com.islamversity.db.model.SajdahTypeFlag
import com.islamversity.db.model.SurahId
import com.islamversity.db.model.SurahOrderId
import com.islamversity.domain.model.FillingStatus
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import com.islamversity.db.model.Calligraphy as CalligraphyCode

private const val SETTINGS_KEY_DATA_BASE_CREATION_VERSION = "SETTINGS_KEY_DATA_BASE_CREATION_VERSION"
private const val SURAH_INDEX_TUBAH = 9L

interface DatabaseFillerUseCase {
    suspend fun needsFilling(): Boolean

    suspend fun fill()

    fun status(): Flow<FillingStatus>
}

interface DatabaseFileConfig {

    val assetVersion: Int

    fun generateRandomUUID(): String

    fun getQuranExtraData(): ByteArray

    fun getQuranArabicText(): ByteArray
    fun arabicTextCalligraphy(): Calligraphy

    fun getQuranEnglishText(): ByteArray
    fun englishTextCalligraphy(): Calligraphy

    fun getQuranFarsiText(): ByteArray
    fun farsiTextCalligraphy(): Calligraphy
}


class DatabaseFillerUseCaseImpl(
    private val db: Main,
    private val config: DatabaseFileConfig
) : DatabaseFillerUseCase {
    private val broadCaster = AtomicReference(BroadcastChannel<FillingStatus>(Channel.CONFLATED).also {
        it.offer(FillingStatus.Idle)
    })

    override suspend fun needsFilling(): Boolean {
        val persistedVersion =
            db
                .settingsQueries
                .getWithKey(SETTINGS_KEY_DATA_BASE_CREATION_VERSION)
                .executeAsOneOrNull()
                ?.value ?: return true

        if (persistedVersion.toInt() < config.assetVersion) {
            return true
        }

        broadCaster.value?.close()

        return false
    }

    override fun status(): Flow<FillingStatus> =
        if (broadCaster.value.isClosedForSend) {
            flowOf(FillingStatus.Done)
        } else {
            broadCaster.value.asFlow()
        }

    private fun randomUUID() = config.generateRandomUUID()

    private data class Surah(
        val id: SurahId,
        val orderIndex: SurahOrderId,
        val revealFlag: RevealTypeFlag,
        val bismillahTypeFlag: BismillahTypeFlag?,
        val names: List<Name>
    )

    private data class Name(
        val id: NameId,
        val rowId: RawId,
        val calligraphy: CalligraphyId,
        val content: String
    )

    private data class Aya(
        val id: AyaId,
        val orderIndex: AyaOrderId,
        val surahId: SurahId,
        val sajdahType: SajdahTypeFlag?,
        val juzOrderIndex: Juz,
        val hizbOrderIndex: HizbQuarter,
        val contents: List<AyaContent>,
        val startOfHizb: Boolean?,
        val endOfHizb: Boolean?
    )

    private data class AyaContent(
        val id: AyaContentId,
        val ayaId: AyaId,
        val calligraphy: CalligraphyId,
        val content: String
    )


    override suspend fun fill() {
        internalFill(db, config, broadCaster)
    }

    private suspend fun internalFill(
        db: Main,
        config: DatabaseFileConfig,
        broadCaster: AtomicReference<BroadcastChannel<FillingStatus>>
    ) {
        CoroutineWorker.execute {
            val arabicTextCalligraphy = config.arabicTextCalligraphy()
            val englishTextCalligraphy = config.englishTextCalligraphy()
            val farsiTextCalligraphy = config.farsiTextCalligraphy()
            broadCaster.value.offer(FillingStatus.Started)
            var quranDataObjectDeferred: Deferred<JsonObject>? = async {
                Json.parseToJsonElement(config.getQuranExtraData().decodeToString())
                    .jsonObject["quran"]!!
                    .jsonObject
            }

            val calligraphyQueries = db.calligraphyQueries
            val surahQueries = db.surahQueries
            val nameQueries = db.nameQueries
            val ayaQueries = db.ayaQueries
            val ayaContentQueries = db.ayaContentQueries



            val arabicCalligraphy = createSurahArabicNameCalligraphy()
            val englishCalligraphy = createSurahEnglishNameCalligraphy()
            val englishTranslatedCalligraphy = createSurahEnglishTranslatedNameCalligraphy()

            calligraphyQueries.transaction {
                //clean the db before we start filling
                calligraphyQueries.deleteAll()
                surahQueries.deleteAll()
                nameQueries.deleteAll()
                ayaQueries.deleteAll()
                ayaContentQueries.deleteAllContents()
//                db.utilsQueries.vacuum()


                calligraphyQueries.insertCalligraphy(arabicTextCalligraphy)
                calligraphyQueries.insertCalligraphy(englishTextCalligraphy)
                calligraphyQueries.insertCalligraphy(farsiTextCalligraphy)


                calligraphyQueries.insertCalligraphy(arabicCalligraphy)
                calligraphyQueries.insertCalligraphy(englishCalligraphy)
                calligraphyQueries.insertCalligraphy(englishTranslatedCalligraphy)
            }
            broadCaster.value.offer(FillingStatus.Filling.CalligraphiesFilled)


            broadCaster.value.offer(FillingStatus.Filling.Filled(30))
            val revealMap = mapOf(
                RevealTypeFlag.MECCAN.raw.toLowerCase()
                    .capitalize() to RevealTypeFlag.MECCAN,
                RevealTypeFlag.MEDINAN.raw.toLowerCase()
                    .capitalize() to RevealTypeFlag.MEDINAN
            )

            var quranDataObject: JsonObject? = quranDataObjectDeferred!!.await()

            val surahs = parseSurah(
                quranDataObject!!,
                revealMap,
                arabicCalligraphy.id,
                englishTranslatedCalligraphy.id,
                englishCalligraphy.id
            )

            broadCaster.value.offer(FillingStatus.Filling.Filled(40))

            val surahOrderIdMap = insertSurahAndGetIdMap(surahs, surahQueries, nameQueries)

            val sajdaMap = getSajdaMap(quranDataObject)

            val juzMap = getJuzMap(quranDataObject)

            val hizbMap = getHizbMap(quranDataObject)

            quranDataObjectDeferred = null
            quranDataObject = null

            broadCaster.value.offer(FillingStatus.Filling.Filled(50))

            val quranArabicJson = getQuranArabicJson(config)

            val quranEnglishJson = getQuranEnglishJson(config)

            val quranFarsiJson = getQuranFarsiJson(config)

            broadCaster.value.offer(FillingStatus.Filling.Filled(70))

            val ayaList = parseAya(
                arabicTextCalligraphy,
                englishTextCalligraphy,
                farsiTextCalligraphy,
                juzMap,
                hizbMap,
                quranArabicJson,
                surahOrderIdMap,
                quranEnglishJson,
                quranFarsiJson,
                sajdaMap,
            )

            broadCaster.value.offer(FillingStatus.Filling.Filled(80))

            insertAyas(ayaQueries, ayaList, ayaContentQueries)

            db.settingsQueries.upsert(SETTINGS_KEY_DATA_BASE_CREATION_VERSION, config.assetVersion.toString())

            broadCaster.value.offer(FillingStatus.Done)
        }
    }

    private fun insertAyas(
        ayaQueries: AyaQueries,
        ayaList: List<Aya>,
        ayaContentQueries: AyaContentQueries
    ) {
        ayaQueries.transaction {
            ayaList.forEach { a ->
                ayaQueries.insertAya(
                    a.id,
                    a.orderIndex,
                    a.surahId,
                    a.sajdahType,
                    a.juzOrderIndex,
                    a.hizbOrderIndex,
                    a.startOfHizb,
                    a.endOfHizb,
                )

                a.contents.forEach { ac ->
                    ayaContentQueries.insertAyaContent(
                        ac.id,
                        ac.ayaId,
                        ac.calligraphy,
                        ac.content
                    )
                }
            }
        }
    }

    private suspend fun getQuranArabicJson(config : DatabaseFileConfig): JsonArray {
        return config
            .getQuranArabicText()
            .decodeToString().let {
                Json.parseToJsonElement(it)
            }
            .jsonObject
            .getValue("quran")
            .jsonArray
    }

    private fun getQuranFarsiJson(config : DatabaseFileConfig): JsonArray {
        return config
            .getQuranFarsiText()
            .decodeToString()
            .let {
                Json.parseToJsonElement(it)
            }
            .jsonArray
    }

    private fun getQuranEnglishJson(config : DatabaseFileConfig): JsonArray {
        return config
            .getQuranEnglishText()
            .decodeToString()
            .let {
                Json.parseToJsonElement(it)
            }
            .jsonArray
    }

    private fun CalligraphyQueries.insertCalligraphy(call: Calligraphy) {
        insertCalligraphy(call.id, call.languageCode, call.name, call.friendlyName, call.code)
    }

    private fun createSurahArabicNameCalligraphy(): Calligraphy {
        val calligraphyId = CalligraphyId(randomUUID())
        val languageCode = LanguageCode("ar")
        val friendlyName = "عربی"
        val calligraphy = CalligraphyCode(languageCode, null)
        return Calligraphy(
            0L,
            calligraphyId,
            languageCode,
            null,
            friendlyName,
            calligraphy
        )
    }

    private fun createSurahEnglishNameCalligraphy(): Calligraphy {
        val calligraphyId = CalligraphyId(randomUUID())
        val languageCode = LanguageCode("en")
        val friendlyName = "English"
        val calligraphy = CalligraphyCode(languageCode, null)
        return Calligraphy(
            0L,
            calligraphyId,
            languageCode,
            null,
            friendlyName,
            calligraphy
        )
    }

    private fun createSurahEnglishTranslatedNameCalligraphy(): Calligraphy {
        val calligraphyId = CalligraphyId(randomUUID())
        val languageCode = LanguageCode("en")
        val name = CalligraphyName("translated")
        val friendlyName = "English (translated)"
        val calligraphy = CalligraphyCode(languageCode, name)
        return Calligraphy(
            0L,
            calligraphyId,
            languageCode,
            name,
            friendlyName,
            calligraphy
        )
    }


    private fun parseSurah(
        quranDataObject: JsonObject,
        revealMap: Map<String, RevealTypeFlag>,
        calligraphyArId: CalligraphyId,
        calligraphyEnTranslatedId: CalligraphyId,
        calligraphyEnId: CalligraphyId
    ): List<Surah> {
        return quranDataObject
            .getValue("suras")
            .jsonObject
            .getValue("sura")
            .jsonArray
            .map {
                val sura = it.jsonObject
                val r = revealMap[sura.getValue("_type").jsonPrimitive.content]!!
                val surahId = SurahId(randomUUID())
                val surahIndex = sura.getValue("_index").jsonPrimitive.content.toLong()

                val names = listOf(
                    Name(
                        NameId(randomUUID()),
                        surahId.raw,
                        calligraphyArId,
                        sura.getValue("_name").jsonPrimitive.content
                    ),
                    Name(
                        NameId(randomUUID()),
                        surahId.raw,
                        calligraphyEnTranslatedId,
                        sura.getValue("_ename").jsonPrimitive.content
                    ),
                    Name(
                        NameId(randomUUID()),
                        surahId.raw,
                        calligraphyEnId,
                        sura.getValue("_tname").jsonPrimitive.content
                    )
                )

                val bismillahFlag = if (surahIndex == 1L) {
                    BismillahTypeFlag.FIRST_AYA
                } else if (surahIndex == SURAH_INDEX_TUBAH) {
                    null
                } else {
                    BismillahTypeFlag.NEEDED
                }

                Surah(
                    surahId,
                    SurahOrderId(surahIndex),
                    r,
                    bismillahFlag,
                    names
                )
            }
    }


    private fun insertSurahAndGetIdMap(
        surahs: List<Surah>,
        surahQueries: SurahQueries,
        nameQueries: NameQueries
    ): Map<SurahOrderId, SurahId> {
        val surahOrderIdMap = mutableMapOf<SurahOrderId, SurahId>()

        surahQueries.transaction {
            surahs.forEach {
                surahOrderIdMap[it.orderIndex] = it.id
                surahQueries.insertSurah(
                    it.id,
                    it.orderIndex,
                    it.revealFlag,
                    it.bismillahTypeFlag
                )
                it.names.forEach { n ->
                    nameQueries.insertName(n.id, n.rowId, n.calligraphy, n.content)
                }
            }
        }

        return surahOrderIdMap
    }


    private fun getSajdaMap(
        quranDataObject: JsonObject
    ): Map<Pair<Long, Long>, SajdahTypeFlag?> {
        return mapOf(
            *quranDataObject
                .getValue("sajdas")
                .jsonObject
                .getValue("sajda")
                .jsonArray
                .map {
                    val sajda = it.jsonObject

                    val key = sajda.getValue("_sura").jsonPrimitive.content.toLong() to
                            sajda.getValue("_aya").jsonPrimitive.content.toLong()

                    val flag = SajdahTypeFlag(sajda.getValue("_type").jsonPrimitive.content.toUpperCase())

                    key to flag
                }
                .toTypedArray()
        )
    }

    private fun getJuzMap(quranDataObject: JsonObject) =
        quranDataObject
            .getValue("juzs")
            .jsonObject
            .getValue("juz")
            .jsonArray
            .map {
                val obj = it.jsonObject
                val key = obj.getValue("_sura").jsonPrimitive.content.toLong() to
                        obj.getValue("_aya").jsonPrimitive.content.toLong()

                val value = Juz(obj.getValue("_index").jsonPrimitive.long)

                key to value
            }
            .toTypedArray()
            .let {
                mapOf(*it)
            }

    private fun getHizbMap(quranDataObject: JsonObject) =
        quranDataObject
            .getValue("hizbs")
            .jsonObject
            .getValue("quarter")
            .jsonArray
            .map {
                val obj = it.jsonObject
                val key = obj.getValue("_sura").jsonPrimitive.content.toLong() to
                        obj.getValue("_aya").jsonPrimitive.content.toLong()

                val value = HizbQuarter(obj.getValue("_index").jsonPrimitive.long)

                key to value
            }
            .toTypedArray()
            .let {
                mapOf(*it)
            }


    private fun parseAya(
        arabicTextCalligraphy : Calligraphy,
        englishTextCalligraphy: Calligraphy,
        farsiTextCalligraphy : Calligraphy,
        juzMap: Map<Pair<Long, Long>, Juz>,
        hizbMap: Map<Pair<Long, Long>, HizbQuarter>,
        quranArabicJson: JsonArray,
        surahOrderIdMap: Map<SurahOrderId, SurahId>,
        quranEnglishJson: JsonArray,
        quranFarsiJson: JsonArray,
        sajdaMap: Map<Pair<Long, Long>, SajdahTypeFlag?>,
    ): List<Aya> {
        val arSimpleCall: CalligraphyId = arabicTextCalligraphy.id
        val enAYACall: CalligraphyId = englishTextCalligraphy.id
        val faAyaCall: CalligraphyId = farsiTextCalligraphy.id

        //juz and hizb has to begin with 1 not zero
        var juz = juzMap[1L to 1L]!!
        var hizb = hizbMap[1L to 1L]!!

        val ayaList = ArrayList<Aya>()

        val surahSize = quranArabicJson.size
        for (i in 0 until surahSize) {
            val arSurah = quranArabicJson[i].jsonObject
            val surahIndex = arSurah["index"]!!.jsonPrimitive.content.toLong()
            val surahId = surahIndex.let { surahOrderIdMap[SurahOrderId(it)] }!!

            val enSurah = quranEnglishJson[i].jsonObject
            val faSurah = quranFarsiJson[i].jsonObject

            assert(surahIndex == enSurah["index"]!!.jsonPrimitive.content.toLong())
            assert(surahIndex == faSurah["index"]!!.jsonPrimitive.content.toLong())

            val enSurahAya = enSurah["aya"]!!.jsonArray

            val faSurahAya = faSurah["aya"]!!.jsonArray

            val ayaSize = arSurah["aya"]!!.jsonArray.size
            for (j in 0 until ayaSize) {
                val arabicAya = arSurah["aya"]!!.jsonArray[j].jsonObject

                val ayaId = AyaId(randomUUID())
                val orderIndex = AyaOrderId(arabicAya.getValue("index").jsonPrimitive.content.toLong())

                val surahOrderAyaOrderPair = surahIndex to orderIndex.order

                val sajdahIdType = sajdaMap[surahOrderAyaOrderPair]

                juz = juzMap[surahOrderAyaOrderPair] ?: juz

                val newHizb = hizbMap[surahOrderAyaOrderPair]
                val startOfHizb = if (newHizb != null) {
                    true
                } else {
                    null
                }
                hizb = newHizb ?: hizb

                val endOfHizb: Boolean?

//                Logger.log(Severity.Error, "DatabaseFiller", null, "ayaOrder= $surahOrderAyaOrderPair, ayaSize=$ayaSize, surahSize=$surahSize, startOfHizb=$startOfHizb")
                if (startOfHizb == null) {

                    if (surahOrderAyaOrderPair.second.toInt() < ayaSize) {
                        val nextAyaOrder = surahIndex to (surahOrderAyaOrderPair.second + 1)
//                        Logger.log(Severity.Error, "DatabaseFiller", null, "nextAya= $nextAyaOrder")
                        endOfHizb = hizbMap[nextAyaOrder]?.let { true }
                    } else if (surahOrderAyaOrderPair.first.toInt() < surahSize) {
                        val nextSurahOrder = (surahIndex + 1) to 1L
//                        Logger.log(Severity.Error, "DatabaseFiller", null, "nextSurah= $nextSurahOrder")
                        endOfHizb = hizbMap[nextSurahOrder]?.let { true }
                    } else {
//                        Logger.log(Severity.Error, "DatabaseFiller", null, "LastAYA")
                        //we are in last aya
                        endOfHizb = true
                    }

                } else {
                    endOfHizb = null
                }

//                Logger.log(Severity.Error, "DatabaseFiller", null, "endOfHizb=$endOfHizb")
                val arAyaContentId = AyaContentId(randomUUID())

                val arabicC = AyaContent(
                    arAyaContentId,
                    ayaId,
                    arSimpleCall,
                    arabicAya.getValue("text").jsonPrimitive.content
                )

                val enAya = enSurahAya[j].jsonObject
                val enAyaContentId = AyaContentId(randomUUID())
                assert(orderIndex.order == enAya.getValue("index").jsonPrimitive.content.toLong())

                val englishC = AyaContent(
                    enAyaContentId,
                    ayaId,
                    enAYACall,
                    enAya.getValue("text").jsonPrimitive.content
                )

                val faAya = faSurahAya[j].jsonObject
                val faAyaContentId = AyaContentId(randomUUID())
                assert(orderIndex.order == faAya.getValue("index").jsonPrimitive.content.toLong())


                val farsiC = AyaContent(
                    faAyaContentId,
                    ayaId,
                    faAyaCall,
                    faAya.getValue("text").jsonPrimitive.content
                )

                val aya = Aya(
                    ayaId,
                    orderIndex,
                    surahId,
                    sajdahIdType,
                    juz,
                    hizb,
                    listOf(
                        arabicC,
                        englishC,
                        farsiC
                    ),
                    startOfHizb,
                    endOfHizb
                )

                ayaList.add(aya)
            }
        }
        return ayaList
    }

    private fun assert(condition: Boolean) {
        if (!condition)
            error("condition not met")
    }
}
