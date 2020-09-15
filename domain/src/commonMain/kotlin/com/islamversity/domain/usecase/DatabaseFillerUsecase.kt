package com.islamversity.domain.usecase

import com.benasher44.uuid.bytes
import com.benasher44.uuid.uuid4
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
import com.islamversity.db.model.BismillahId
import com.islamversity.db.model.BismillahTypeFlag
import com.islamversity.db.model.CalligraphyId
import com.islamversity.db.model.CalligraphyName
import com.islamversity.db.model.HizbQuarter
import com.islamversity.db.model.Juz
import com.islamversity.db.model.LanguageCode
import com.islamversity.db.model.NameId
import com.islamversity.db.model.RawId
import com.islamversity.db.model.RevealTypeFlag
import com.islamversity.db.model.RevealTypeId
import com.islamversity.db.model.SajdahId
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

interface DatabaseFillerUseCase {
    suspend fun needsFilling(): Boolean

    suspend fun fill()

    fun status(): Flow<FillingStatus>
}

interface DatabaseFileConfig {

    val assetVersion: Int

    fun getQuranExtraData(): ByteArray

    fun getQuranArabicText(): ByteArray
    val arabicTextCalligraphy: Calligraphy

    fun getQuranEnglishText(): ByteArray
    val englishTextCalligraphy: Calligraphy

    fun getQuranFarsiText(): ByteArray
    val farsiTextCalligraphy: Calligraphy
}


class DatabaseFillerUseCaseImpl(
    private val db: Main,
    private val config: DatabaseFileConfig
) : DatabaseFillerUseCase {
    private val broadCaster by lazy {
        BroadcastChannel<FillingStatus>(Channel.CONFLATED)
    }

    override suspend fun needsFilling(): Boolean {
        val persistedVersion =
            db.settingsQueries.getWithKey(SETTINGS_KEY_DATA_BASE_CREATION_VERSION).executeAsOneOrNull() ?: return true

        if (persistedVersion.value.toInt() < config.assetVersion) {
            return true
        }

        broadCaster.close()

        return false
    }

    override fun status(): Flow<FillingStatus> =
        if (broadCaster.isClosedForSend) {
            flowOf(FillingStatus.Done)
        } else {
            broadCaster.asFlow()
        }

    private fun randomUUID() = uuid4().bytes.decodeToString()

    private data class Surah(
        val id: SurahId,
        val orderIndex: SurahOrderId,
        val revealType: RevealTypeId,
        val revealFlag: RevealTypeFlag,
        val bismillahTypeFlag: BismillahTypeFlag,
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
        val sajdahId: SajdahId,
        val sajdahType: SajdahTypeFlag,
        val juzOrderIndex: Juz,
        val hizbOrderIndex: HizbQuarter,
        val contents: List<AyaContent>
    )

    private data class AyaContent(
        val id: AyaContentId,
        val ayaId: AyaId,
        val calligraphy: CalligraphyId,
        val content: String
    )


    override suspend fun fill() {
        coroutineScope {

            broadCaster.offer(FillingStatus.Started)
            var quranDataObjectDeferred: Deferred<JsonObject>? = async {
                Json.parseToJsonElement(config.getQuranExtraData().decodeToString())
                    .jsonObject["quran"]!!
                    .jsonObject
            }

            val calligraphyQueries = db.calligraphyQueries
            val revealTypeQueries = db.suraRevealTypeQueries
            val sajdahQueries = db.sajdahQueries
            val surahQueries = db.surahQueries
            val nameQueries = db.nameQueries
            val bismillahQueries = db.bismillahQueries
            val ayaQueries = db.ayaQueries
            val ayaContentQueries = db.ayaContentQueries

            val arabicCalligraphy = createSurahArabicNameCalligraphy()
            val englishCalligraphy = createSurahEnglishNameCalligraphy()
            val englishTranslatedCalligraphy = createSurahEnglishTranslatedNameCalligraphy()

            calligraphyQueries.transaction {
                calligraphyQueries.insertCalligraphy(config.arabicTextCalligraphy)
                calligraphyQueries.insertCalligraphy(config.englishTextCalligraphy)
                calligraphyQueries.insertCalligraphy(config.farsiTextCalligraphy)


                calligraphyQueries.insertCalligraphy(arabicCalligraphy)
                calligraphyQueries.insertCalligraphy(englishCalligraphy)
                calligraphyQueries.insertCalligraphy(englishTranslatedCalligraphy)
            }
            broadCaster.offer(FillingStatus.Filling.CalligraphiesFilled)

            val meccaRevealId = RevealTypeId(randomUUID())
            val medinanRevealId = RevealTypeId(randomUUID())
            revealTypeQueries.transaction {
                revealTypeQueries.insertType(meccaRevealId, RevealTypeFlag.MECCAN)
                revealTypeQueries.insertType(medinanRevealId, RevealTypeFlag.MEDINAN)
            }

            broadCaster.offer(FillingStatus.Filling.Filled(15))

            val sajdahObligatoryId = SajdahId(randomUUID())
            val sajdahRecommendedId = SajdahId(randomUUID())
            val sajdahNoneId = SajdahId(randomUUID())
            sajdahQueries.transaction {
                sajdahQueries.insertSajdah(sajdahObligatoryId, SajdahTypeFlag.OBLIGATORY)
                sajdahQueries.insertSajdah(sajdahRecommendedId, SajdahTypeFlag.RECOMMENDED)
                sajdahQueries.insertSajdah(sajdahNoneId, SajdahTypeFlag.NONE)
            }

            broadCaster.offer(FillingStatus.Filling.Filled(20))
            val bismillahId = BismillahId(randomUUID())
            bismillahQueries.insertBismillah(bismillahId, BismillahTypeFlag.NEEDED)

            nameQueries.insertRevealAndSajdahTypeAndBismillahNames(
                meccaRevealId,
                arabicCalligraphy.id,
                medinanRevealId,
                sajdahObligatoryId,
                sajdahRecommendedId,
                sajdahNoneId,
                englishCalligraphy.id,
                englishTranslatedCalligraphy.id,
                bismillahId,
            )

            broadCaster.offer(FillingStatus.Filling.Filled(30))
            val revealMap = mapOf(
                RevealTypeFlag.MECCAN.raw.toLowerCase()
                    .capitalize() to (meccaRevealId to RevealTypeFlag.MECCAN),
                RevealTypeFlag.MEDINAN.raw.toLowerCase()
                    .capitalize() to (medinanRevealId to RevealTypeFlag.MEDINAN)
            )

            var quranDataObject: JsonObject? = quranDataObjectDeferred!!.await()

            val surahs = parseSurah(
                quranDataObject!!,
                revealMap,
                arabicCalligraphy.id,
                englishTranslatedCalligraphy.id,
                englishCalligraphy.id
            )

            broadCaster.offer(FillingStatus.Filling.Filled(40))

            val sajdaIdMap = mapOf(
                SajdahTypeFlag.RECOMMENDED to sajdahRecommendedId,
                SajdahTypeFlag.OBLIGATORY to sajdahObligatoryId,
                SajdahTypeFlag.NONE to sajdahNoneId
            )

            val surahOrderIdMap = insertSurahAndGetIdMap(surahs, surahQueries, nameQueries)

            val sajdaMap = getSajdaMap(quranDataObject, sajdaIdMap)

            val juzMap = getJuzMap(quranDataObject)

            val hizbMap = getHizbMap(quranDataObject)

            quranDataObjectDeferred = null
            quranDataObject = null

            broadCaster.offer(FillingStatus.Filling.Filled(50))

            val quranArabicJson = getQuranArabicJson()

            val quranEnglishJson = getQuranEnglishJson()

            val quranFarsiJson = getQuranFarsiJson()

            val ayaList = parseAya(
                juzMap,
                hizbMap,
                quranArabicJson,
                surahOrderIdMap,
                quranEnglishJson,
                quranFarsiJson,
                sajdaMap,
                sajdahNoneId,
            )

            broadCaster.offer(FillingStatus.Filling.Filled(80))

            insertAyas(ayaQueries, ayaList, ayaContentQueries)

            db.settingsQueries.upsert(SETTINGS_KEY_DATA_BASE_CREATION_VERSION, config.assetVersion.toString())

            broadCaster.offer(FillingStatus.Done)
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
                    a.sajdahId,
                    a.sajdahType,
                    a.juzOrderIndex,
                    a.hizbOrderIndex
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

    private fun getQuranArabicJson(): JsonArray {
        return config
            .getQuranArabicText()
            .decodeToString().let {
                Json.parseToJsonElement(it)
            }
            .jsonObject
            .getValue("quran")
            .jsonArray
    }

    private fun getQuranFarsiJson(): JsonArray {
        return config
            .getQuranFarsiText()
            .decodeToString()
            .let {
                Json.parseToJsonElement(it)
            }
            .jsonArray
    }

    private fun getQuranEnglishJson(): JsonArray {
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

    private fun NameQueries.insertRevealAndSajdahTypeAndBismillahNames(
        meccaRevealId: RevealTypeId,
        calligraphyArId: CalligraphyId,
        medinanRevealId: RevealTypeId,
        sajdahObligatoryId: SajdahId,
        sajdahRecommendedId: SajdahId,
        sajdahNoneId: SajdahId,
        calligraphyEnId: CalligraphyId,
        calligraphyEnTranslatedId: CalligraphyId,
        bismillahId: BismillahId,
    ) {
        transaction {
            val meccaArId = NameId(randomUUID())
            val medinanArId = NameId(randomUUID())
            insertName(meccaArId, meccaRevealId.raw, calligraphyArId, "مكية")
            insertName(medinanArId, medinanRevealId.raw, calligraphyArId, "مدنية")

            val obligatoryArId = NameId(randomUUID())
            val recommendedArId = NameId(randomUUID())
            val noneArId = NameId(randomUUID())
            insertName(
                obligatoryArId,
                sajdahObligatoryId.raw,
                calligraphyArId,
                "الواجبة"
            )
            insertName(recommendedArId, sajdahRecommendedId.raw, calligraphyArId, "أوصت")
            insertName(noneArId, sajdahNoneId.raw, calligraphyArId, "لا حاجة")

            //english-Anglicized
            val meccaEnAngId = NameId(randomUUID())
            val medinanEnAngId = NameId(randomUUID())
            insertName(
                meccaEnAngId,
                meccaRevealId.raw,
                calligraphyEnId,
                RevealTypeFlag.MECCAN.raw.toLowerCase().capitalize()
            )
            insertName(
                medinanEnAngId,
                medinanRevealId.raw,
                calligraphyEnId,
                RevealTypeFlag.MEDINAN.raw.toLowerCase().capitalize()
            )

            val obligatoryEnAngId = NameId(randomUUID())
            val recommendedEnAngId = NameId(randomUUID())
            val noneEnAngId = NameId(randomUUID())
            insertName(
                obligatoryEnAngId,
                sajdahObligatoryId.raw,
                calligraphyEnId,
                SajdahTypeFlag.OBLIGATORY.raw.toLowerCase().capitalize()
            )
            insertName(
                recommendedEnAngId,
                sajdahRecommendedId.raw,
                calligraphyEnId,
                SajdahTypeFlag.RECOMMENDED.raw.toLowerCase().capitalize()
            )
            insertName(
                noneEnAngId,
                sajdahNoneId.raw,
                calligraphyEnId,
                SajdahTypeFlag.NONE.raw.toLowerCase().capitalize()
            )


            //english
            val meccaEnId = NameId(randomUUID())
            val medinanEnId = NameId(randomUUID())
            insertName(
                meccaEnId,
                meccaRevealId.raw,
                calligraphyEnTranslatedId,
                RevealTypeFlag.MECCAN.raw.toLowerCase().capitalize()
            )
            insertName(
                medinanEnId,
                medinanRevealId.raw,
                calligraphyEnTranslatedId,
                RevealTypeFlag.MEDINAN.raw.toLowerCase().capitalize()
            )

            val obligatoryEnId = NameId(randomUUID())
            val recommendedEnId = NameId(randomUUID())
            val noneEnId = NameId(randomUUID())
            insertName(
                obligatoryEnId,
                sajdahObligatoryId.raw,
                calligraphyEnTranslatedId,
                SajdahTypeFlag.OBLIGATORY.raw.toLowerCase().capitalize()
            )
            insertName(
                recommendedEnId,
                sajdahRecommendedId.raw,
                calligraphyEnTranslatedId,
                SajdahTypeFlag.RECOMMENDED.raw.toLowerCase().capitalize()
            )
            insertName(
                noneEnId,
                sajdahNoneId.raw,
                calligraphyArId,
                SajdahTypeFlag.NONE.raw.toLowerCase().capitalize()
            )


            //bismillah
            insertName(
                NameId(randomUUID()),
                bismillahId.raw,
                config.arabicTextCalligraphy.id,
                "بِسْمِ اللَّهِ الرَّحْمَنِ الرَّحِيمِ"
            )
            insertName(
                NameId(randomUUID()),
                bismillahId.raw,
                config.farsiTextCalligraphy.id,
                "به نام خداوند بخشنده بخشایشگر"
            )
            insertName(
                NameId(randomUUID()),
                bismillahId.raw,
                config.englishTextCalligraphy.id,
                "In the name of Allah, Most Gracious, Most Merciful."
            )
        }
    }


    private fun parseSurah(
        quranDataObject: JsonObject,
        revealMap: Map<String, Pair<RevealTypeId, RevealTypeFlag>>,
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
                } else if (surahIndex == 9L) {
                    BismillahTypeFlag.NONE
                } else {
                    BismillahTypeFlag.NEEDED
                }

                Surah(
                    surahId,
                    SurahOrderId(surahIndex),
                    r.first,
                    r.second,
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
                    it.revealType,
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
        quranDataObject: JsonObject,
        sajdaIdMap: Map<SajdahTypeFlag, SajdahId>
    ): Map<Pair<Long, Long>, Pair<SajdahId, SajdahTypeFlag>> {
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

                    val value = sajdaIdMap[flag]!! to flag
                    key to value
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
        juzMap: Map<Pair<Long, Long>, Juz>,
        hizbMap: Map<Pair<Long, Long>, HizbQuarter>,
        quranArabicJson: JsonArray,
        surahOrderIdMap: Map<SurahOrderId, SurahId>,
        quranEnglishJson: JsonArray,
        quranFarsiJson: JsonArray,
        sajdaMap: Map<Pair<Long, Long>, Pair<SajdahId, SajdahTypeFlag>>,
        sajdahNoneId: SajdahId,
    ): List<Aya> {
        val arSimpleCall: CalligraphyId = config.arabicTextCalligraphy.id
        val enAYACall: CalligraphyId = config.englishTextCalligraphy.id
        val faAyaCall: CalligraphyId = config.farsiTextCalligraphy.id

        //juz and hizb has to begin with 1 not zero
        var juz = juzMap[1L to 1L]!!
        var hizb = hizbMap[1L to 1L]!!

        val ayaList = ArrayList<Aya>()

        for (i in 0 until quranArabicJson.size) {
            val arSurah = quranArabicJson[i].jsonObject
            val surahIndex = arSurah["index"]!!.jsonPrimitive.content.toLong()
            val surahId = surahIndex.let { surahOrderIdMap[SurahOrderId(it)] }!!

            val enSurah = quranEnglishJson[i].jsonObject
            val faSurah = quranFarsiJson[i].jsonObject

            assert(surahIndex == enSurah["index"]!!.jsonPrimitive.content.toLong())
            assert(surahIndex == faSurah["index"]!!.jsonPrimitive.content.toLong())

            val enSurahAya = enSurah["aya"]!!.jsonArray

            val faSurahAya = faSurah["aya"]!!.jsonArray

            for (j in 0 until arSurah["aya"]!!.jsonArray.size) {
                val arabicAya = quranArabicJson[i].jsonObject["aya"]!!.jsonArray[j].jsonObject

                val ayaId = AyaId(randomUUID())
                val orderIndex = AyaOrderId(arabicAya.getValue("index").jsonPrimitive.content.toLong())

                val surahOrderAyaOrderPair = surahIndex to orderIndex.order

                val sajdahIdType =
                    sajdaMap[surahOrderAyaOrderPair] ?: sajdahNoneId to SajdahTypeFlag.NONE

                juz = juzMap[surahOrderAyaOrderPair] ?: juz

                hizb = hizbMap[surahOrderAyaOrderPair] ?: hizb


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
                    sajdahIdType.first,
                    sajdahIdType.second,
                    juz,
                    hizb,
                    listOf(
                        arabicC, englishC,
                        farsiC
                    )
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
