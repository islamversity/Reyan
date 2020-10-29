package com.islamversity.db_test.db_filler

import android.os.Build
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.islamversity.db.*
import com.islamversity.db.model.*
import com.islamversity.db.model.Aya
import com.islamversity.db.model.Calligraphy
import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.serialization.json.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.util.*

/*
data class S(
    val id: SurahId,
    val orderIndex: SurahOrderId,
    val revealType: RevealTypeId,
    val revealFlag: RevealTypeFlag,
    val bismillahTypeFlag: BismillahTypeFlag,
    val names: List<N>
)

data class N(
    val id: NameId,
    val rowId: RawId,
    val calligraphy: CalligraphyId,
    val content: String
)

data class A(
    val id: AyaId,
    val orderIndex: AyaOrderId,
    val surahId: SurahId,
    val sajdahId: SajdahId,
    val sajdahType: SajdahTypeFlag,
    val juzOrderIndex: Juz,
    val hizbOrderIndex: HizbQuarter,
    val contents: List<AC>
)

data class AC(
    val id: AyaContentId,
    val ayaId: AyaId,
    val calligraphy: CalligraphyId,
    val content: String
)

@ExperimentalStdlibApi
@RunWith(AndroidJUnit4::class)
class InsertQuranTextTest {

    lateinit var driver: SqlDriver
    lateinit var db: Main
    val dbName = "Main.db"

    @Before
    fun setup() {
        val file = InstrumentationRegistry.getInstrumentation().targetContext.getDatabasePath(dbName)

        file.delete()

        driver = AndroidSqliteDriver(
            Main.Schema,
            InstrumentationRegistry.getInstrumentation().targetContext,
            dbName,
            useNoBackupDirectory = false
        )

        db = createMainDB(driver)
    }

    @After
    fun tearDown() {
//        val file = File(InstrumentationRegistry.getInstrumentation().targetContext.getNoBackupFilesDir(), dbName)
//        assertThat(file.exists()).isTrue()
//        driver.close()
//        file.delete()
    }

    @Test
    fun parsingAndInsertingWithCoroutines() = runBlockingTest {
        val testBeginningTime = System.currentTimeMillis()

        val quranDataText = InstrumentationRegistry.getInstrumentation().targetContext
            .resources
            .assets
            .open("quran-data.json")
            .use {
                val bytes = ByteArray(it.available())
                it.read(bytes)
                bytes.decodeToString()
            }
        val quranDataObject = Json.parseToJsonElement(quranDataText)
            .jsonObject
            .get("quran")!!.jsonObject

        val quranArabicJson = InstrumentationRegistry.getInstrumentation().targetContext
            .resources
            .assets
            .open("quran-simple-one-line.json")
            .use {
                val bytes = ByteArray(it.available())
                it.read(bytes)
                bytes.decodeToString()
            }
            .let {
                Json.parseToJsonElement(it)
            }
            .jsonObject
            .getValue("quran")
            .jsonArray

        val quranEnglishJson = InstrumentationRegistry.getInstrumentation().targetContext
            .resources
            .assets
            .open("quran-en-Abdullah-Yusuf-Ali.json")
            .use {
                val bytes = ByteArray(it.available())
                it.read(bytes)
                bytes.decodeToString()
            }
            .let {
                Json.parseToJsonElement(it)
            }
            .jsonArray

        val quranFarsiJson = InstrumentationRegistry.getInstrumentation().targetContext
            .resources
            .assets
            .open("quran-farsi-makarem.json")
            .use {
                val bytes = ByteArray(it.available())
                it.read(bytes)
                bytes.decodeToString()
            }
            .let {
                Json.parseToJsonElement(it)
            }
            .jsonArray


        val surahQueries = db.surahQueries
        val nameQueries = db.nameQueries
        val bismillahQueries = db.bismillahQueries
        val ayaQueries = db.ayaQueries
        val ayaCQueries = db.ayaContentQueries


        val calligraphyArId = calligraphyArabicFiller()
        val calligraphyEnAngId = calligraphyEnglishAnglicizedFiller()
        val calligraphyEnId = calligraphyEnglishFiller()


        //reveal types
        val meccaRevealId = RevealTypeId(getRandomUUID())
        val medinanRevealId = RevealTypeId(getRandomUUID())
        db.suraRevealTypeQueries.insertType(meccaRevealId, RevealTypeFlag.MECCAN)
        db.suraRevealTypeQueries.insertType(medinanRevealId, RevealTypeFlag.MEDINAN)

        //sajdah types
        val sajdahObligatoryId = SajdahId(getRandomUUID())
        val sajdahRecommendedId = SajdahId(getRandomUUID())
        val sajdahNoneId = SajdahId(getRandomUUID())
        db.sajdahQueries.insertSajdah(sajdahObligatoryId, SajdahTypeFlag.OBLIGATORY)
        db.sajdahQueries.insertSajdah(sajdahRecommendedId, SajdahTypeFlag.RECOMMENDED)
        db.sajdahQueries.insertSajdah(sajdahNoneId, SajdahTypeFlag.NONE)

        //bismillah types
        val bismillahId = BismillahId(getRandomUUID())
        bismillahQueries.insertBismillah(bismillahId, BismillahTypeFlag.NEEDED)

        insertRevealAndSajdahTypeNames(
            meccaRevealId,
            calligraphyArId,
            medinanRevealId,
            sajdahObligatoryId,
            sajdahRecommendedId,
            sajdahNoneId,
            calligraphyEnAngId,
            calligraphyEnId
        )

        val revealMap = mapOf(
            RevealTypeFlag.MECCAN.raw.toLowerCase()
                .capitalize() to (meccaRevealId to RevealTypeFlag.MECCAN),
            RevealTypeFlag.MEDINAN.raw.toLowerCase()
                .capitalize() to (medinanRevealId to RevealTypeFlag.MEDINAN)
        )

        val surahs = parseSurah(
            quranDataObject,
            revealMap,
            calligraphyArId,
            calligraphyEnId,
            calligraphyEnAngId
        )

        val sajdaIdMap = mapOf(
            SajdahTypeFlag.RECOMMENDED to sajdahRecommendedId,
            SajdahTypeFlag.OBLIGATORY to sajdahObligatoryId,
            SajdahTypeFlag.NONE to sajdahNoneId
        )

        val surahOrderIdMap = insertSurahAndGetIdMap(surahs, surahQueries, nameQueries)

        val sajdaMap = getSajdaMap(quranDataObject, sajdaIdMap)

        val juzMap = getJuzMap(quranDataObject)

        val hizbMap = getHizbMap(quranDataObject)

        //we should insert calligraphy (3 types)for aya_content and then bismillah
        val arabicSimpleCall = CalligraphyId(getRandomUUID())
        val arLang = LanguageCode("ar")
        val arCallName = CalligraphyName("simple")
        db.calligraphyQueries.insertCalligraphy(
            arabicSimpleCall,
            arLang,
            arCallName,
            "عربي-بسيط",
            Calligraphy(arLang, arCallName)
        )
        val enAYACall = CalligraphyId(getRandomUUID())
        val enAYALang = LanguageCode("en")
        val enAYACallName = CalligraphyName("Abdullah Yusuf Ali")
        db.calligraphyQueries.insertCalligraphy(
            enAYACall,
            enAYALang,
            enAYACallName,
            "English-Yusuf Ali",
            Calligraphy(enAYALang, enAYACallName)
        )
        val faMakaremCall = CalligraphyId(getRandomUUID())
        val faMakaremLang = LanguageCode("fa")
        val faMakaremCallName = CalligraphyName("مکارم شیرازی")
        db.calligraphyQueries.insertCalligraphy(
            faMakaremCall,
            faMakaremLang,
            faMakaremCallName,
            "فارسی-مکارم شیرازی",
            Calligraphy(faMakaremLang, faMakaremCallName)
        )

        val ayaList = parseAya(
            juzMap,
            hizbMap,
            quranArabicJson,
            surahOrderIdMap,
            quranEnglishJson,
            quranFarsiJson,
            sajdaMap,
            sajdahNoneId,
            arabicSimpleCall,
            enAYACall,
            faMakaremCall
        )

        nameQueries.insertName(
            NameId(getRandomUUID()),
            bismillahId.raw,
            arabicSimpleCall,
            "بِسْمِ اللَّهِ الرَّحْمَنِ الرَّحِيمِ"
        )
        nameQueries.insertName(
            NameId(getRandomUUID()),
            bismillahId.raw,
            faMakaremCall,
            "به نام خداوند بخشنده بخشایشگر"
        )
        nameQueries.insertName(
            NameId(getRandomUUID()),
            bismillahId.raw,
            enAYACall,
            "In the name of Allah, Most Gracious, Most Merciful."
        )

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
                    ayaCQueries.insertAyaContent(
                        ac.id,
                        ac.ayaId,
                        ac.calligraphy,
                        ac.content
                    )
                }
            }
        }

        val timeTaken = System.currentTimeMillis() - testBeginningTime

        Log.d("Database filler", "total time took to fill the databse=$timeTaken")
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
        arabicSimpleCall: CalligraphyId,
        enAYACall: CalligraphyId,
        faMakaremCall: CalligraphyId
    ): ArrayList<A> {
        var juz = juzMap[1L to 1L]!!
        var hizb = hizbMap[1L to 1L]!!

        val ayaList = ArrayList<A>()

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

                val ayaId = AyaId(getRandomUUID())
                val orderIndex = AyaOrderId(arabicAya.getValue("index").jsonPrimitive.content.toLong())

                val surahOrderAyaOrderPair = surahIndex to orderIndex.order

                val sajdahIdType =
                    sajdaMap[surahOrderAyaOrderPair] ?: sajdahNoneId to SajdahTypeFlag.NONE

                juz = juzMap[surahOrderAyaOrderPair] ?: juz

                hizb = hizbMap[surahOrderAyaOrderPair] ?: hizb


                val arAyaContentId = AyaContentId(getRandomUUID())

                val arabicC = AC(
                    arAyaContentId,
                    ayaId,
                    arabicSimpleCall,
                    arabicAya.getValue("text").jsonPrimitive.content
                )

                val enAya = enSurahAya[j].jsonObject
                val enAyaContentId = AyaContentId(getRandomUUID())
                assert(orderIndex.order == enAya.getValue("index").jsonPrimitive.content.toLong())


                val englishC = AC(
                    enAyaContentId,
                    ayaId,
                    enAYACall,
                    enAya.getValue("text").jsonPrimitive.content
                )

                val faAya = faSurahAya[j].jsonObject
                val faAyaContentId = AyaContentId(getRandomUUID())
                assert(orderIndex.order == faAya.getValue("index").jsonPrimitive.content.toLong())


                val farsiC = AC(
                    faAyaContentId,
                    ayaId,
                    faMakaremCall,
                    faAya.getValue("text").jsonPrimitive.content
                )

                val aya = A(
                    ayaId,
                    orderIndex,
                    surahId,
                    sajdahIdType.first,
                    sajdahIdType.second,
                    juz,
                    hizb,
                    listOf(
                        arabicC
                        , englishC,
                        farsiC
                    )
                )

                ayaList.add(aya)
            }
        }
        return ayaList
    }

    private fun insertSurahAndGetIdMap(
        surahs: List<S>,
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


    private fun insertRevealAndSajdahTypeNames(
        meccaRevealId: RevealTypeId,
        calligraphyArId: CalligraphyId,
        medinanRevealId: RevealTypeId,
        sajdahObligatoryId: SajdahId,
        sajdahRecommendedId: SajdahId,
        sajdahNoneId: SajdahId,
        calligraphyEnAngId: CalligraphyId,
        calligraphyEnId: CalligraphyId
    ) {
        val meccaArId = NameId(getRandomUUID())
        val medinanArId = NameId(getRandomUUID())
        db.nameQueries.insertName(meccaArId, meccaRevealId.raw, calligraphyArId, "مكية")
        db.nameQueries.insertName(medinanArId, medinanRevealId.raw, calligraphyArId, "مدنية")

        val obligatoryArId = NameId(getRandomUUID())
        val recommendedArId = NameId(getRandomUUID())
        val noneArId = NameId(getRandomUUID())
        db.nameQueries.insertName(
            obligatoryArId,
            sajdahObligatoryId.raw,
            calligraphyArId,
            "الواجبة"
        )
        db.nameQueries.insertName(recommendedArId, sajdahRecommendedId.raw, calligraphyArId, "أوصت")
        db.nameQueries.insertName(noneArId, sajdahNoneId.raw, calligraphyArId, "لا حاجة")

        //english-Anglicized
        val meccaEnAngId = NameId(getRandomUUID())
        val medinanEnAngId = NameId(getRandomUUID())
        db.nameQueries.insertName(
            meccaEnAngId,
            meccaRevealId.raw,
            calligraphyEnAngId,
            RevealTypeFlag.MECCAN.raw.toLowerCase().capitalize()
        )
        db.nameQueries.insertName(
            medinanEnAngId,
            medinanRevealId.raw,
            calligraphyEnAngId,
            RevealTypeFlag.MEDINAN.raw.toLowerCase().capitalize()
        )

        val obligatoryEnAngId = NameId(getRandomUUID())
        val recommendedEnAngId = NameId(getRandomUUID())
        val noneEnAngId = NameId(getRandomUUID())
        db.nameQueries.insertName(
            obligatoryEnAngId,
            sajdahObligatoryId.raw,
            calligraphyEnAngId,
            SajdahTypeFlag.OBLIGATORY.raw.toLowerCase().capitalize()
        )
        db.nameQueries.insertName(
            recommendedEnAngId,
            sajdahRecommendedId.raw,
            calligraphyEnAngId,
            SajdahTypeFlag.RECOMMENDED.raw.toLowerCase().capitalize()
        )
        db.nameQueries.insertName(
            noneEnAngId,
            sajdahNoneId.raw,
            calligraphyEnAngId,
            SajdahTypeFlag.NONE.raw.toLowerCase().capitalize()
        )


        //english
        val meccaEnId = NameId(getRandomUUID())
        val medinanEnId = NameId(getRandomUUID())
        db.nameQueries.insertName(
            meccaEnId,
            meccaRevealId.raw,
            calligraphyEnId,
            RevealTypeFlag.MECCAN.raw.toLowerCase().capitalize()
        )
        db.nameQueries.insertName(
            medinanEnId,
            medinanRevealId.raw,
            calligraphyEnId,
            RevealTypeFlag.MEDINAN.raw.toLowerCase().capitalize()
        )

        val obligatoryEnId = NameId(getRandomUUID())
        val recommendedEnId = NameId(getRandomUUID())
        val noneEnId = NameId(getRandomUUID())
        db.nameQueries.insertName(
            obligatoryEnId,
            sajdahObligatoryId.raw,
            calligraphyEnId,
            SajdahTypeFlag.OBLIGATORY.raw.toLowerCase().capitalize()
        )
        db.nameQueries.insertName(
            recommendedEnId,
            sajdahRecommendedId.raw,
            calligraphyEnId,
            SajdahTypeFlag.RECOMMENDED.raw.toLowerCase().capitalize()
        )
        db.nameQueries.insertName(
            noneEnId,
            sajdahNoneId.raw,
            calligraphyArId,
            SajdahTypeFlag.NONE.raw.toLowerCase().capitalize()
        )
    }

    private fun parseSurah(
        quranDataObject: JsonObject,
        revealMap: Map<String, Pair<RevealTypeId, RevealTypeFlag>>,
        calligraphyArId: CalligraphyId,
        calligraphyEnId: CalligraphyId,
        calligraphyEnAngId: CalligraphyId
    ): List<S> {
        return quranDataObject
            .getValue("suras")
            .jsonObject
            .getValue("sura")
            .jsonArray
            .map {
                val sura = it.jsonObject
                val r = revealMap[sura.getValue("_type").jsonPrimitive.content]!!
                val surahId = SurahId(getRandomUUID())
                val surahIndex = sura.getValue("_index").jsonPrimitive.content.toLong()

                val names = listOf(
                    N(
                        NameId(getRandomUUID()),
                        surahId.raw,
                        calligraphyArId,
                        sura.getValue("_name").jsonPrimitive.content
                    ),
                    N(
                        NameId(getRandomUUID()),
                        surahId.raw,
                        calligraphyEnId,
                        sura.getValue("_ename").jsonPrimitive.content
                    ),
                    N(
                        NameId(getRandomUUID()),
                        surahId.raw,
                        calligraphyEnAngId,
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

                S(
                    surahId,
                    SurahOrderId(surahIndex),
                    r.first,
                    r.second,
                    bismillahFlag,
                    names
                )
            }
    }

    private fun calligraphyArabicFiller(): CalligraphyId {
        val calligraphyId = CalligraphyId(getRandomUUID())
        val languageCode = LanguageCode("ar")
        val friendlyName = "عربی"
        val calligraphy = Calligraphy(languageCode, null)

        db.calligraphyQueries.insertCalligraphy(
            calligraphyId,
            languageCode,
            null,
            friendlyName,
            calligraphy
        )
        return calligraphyId
    }

    private fun calligraphyEnglishFiller(): CalligraphyId {
        val calligraphyId = CalligraphyId(getRandomUUID())
        val languageCode = LanguageCode("en")
        val friendlyName = "English"
        val calligraphy = Calligraphy(languageCode, null)

        db.calligraphyQueries.insertCalligraphy(
            calligraphyId,
            languageCode,
            null,
            friendlyName,
            calligraphy
        )
        return calligraphyId
    }

    private fun calligraphyEnglishAnglicizedFiller(): CalligraphyId {
        val calligraphyId = CalligraphyId(getRandomUUID())
        val languageCode = LanguageCode("en")
        val calligraphyName = CalligraphyName("Anglicized")
        val friendlyName = "English-Anglicized"
        val calligraphy = Calligraphy(languageCode, calligraphyName)

        db.calligraphyQueries.insertCalligraphy(
            calligraphyId,
            languageCode,
            calligraphyName,
            friendlyName,
            calligraphy
        )
        return calligraphyId
    }

    private fun getRandomUUID() = UUID.randomUUID().toString()
}
*/