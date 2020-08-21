package com.islamversity.db_test.db_filler

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.islamversity.db.*
import com.islamversity.db.model.*
import com.islamversity.db.model.Calligraphy
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.sql.RowId
import java.util.*

@ExperimentalStdlibApi
@RunWith(AndroidJUnit4::class)
class InsertQuranTextTest {

    lateinit var driver: SqlDriver
    lateinit var db: Main
    val dbName = "Main.db"

    @Before
    fun setup() {
        val file = File(
            InstrumentationRegistry.getInstrumentation().targetContext.getNoBackupFilesDir(),
            dbName
        )
        file.delete()

        driver = AndroidSqliteDriver(
            Main.Schema,
            InstrumentationRegistry.getInstrumentation().targetContext,
            dbName,
            useNoBackupDirectory = true
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


        //region sajdah and reveal type names
        //inserting names
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
//endregion

        val revealMap = mapOf(
            RevealTypeFlag.MECCAN.raw.toLowerCase()
                .capitalize() to (meccaRevealId to RevealTypeFlag.MECCAN),
            RevealTypeFlag.MEDINAN.raw.toLowerCase()
                .capitalize() to (medinanRevealId to RevealTypeFlag.MEDINAN)
        )
        val surahQueries = db.surahQueries
        val nameQueries = db.nameQueries

        val surahs = Json.parseJson(quranDataText)
            .jsonObject
            .getObject("quran")
            .jsonObject
            .getObject("suras")
            .jsonObject
            .getArray("sura")
            .map {
                val sura = it.jsonObject
                val r = revealMap[sura.getPrimitive("_type").content]!!
                val surahId = SurahId(getRandomUUID())
                val names = listOf(
                    N(
                        NameId(getRandomUUID()),
                        surahId.raw,
                        calligraphyArId,
                        sura.getPrimitive("_name").content
                    ),
                    N(
                        NameId(getRandomUUID()),
                        surahId.raw,
                        calligraphyEnId,
                        sura.getPrimitive("_ename").content
                    ),
                    N(
                        NameId(getRandomUUID()),
                        surahId.raw,
                        calligraphyEnAngId,
                        sura.getPrimitive("_tname").content
                    )
                )


                S(
                    surahId,
                    SurahOrderId(sura.getPrimitive("index").content.toLong()),
                    r.first,
                    r.second,
                    names
                )
            }

        surahs.forEach {
            surahQueries.insertSurah(it.id, it.orderIndex, it.revealType, it.revealFlag)
            it.names.forEach { n ->
                nameQueries.insertName(n.id, n.rowId, n.calligraphy, n.content)
            }
        }


//        val ayaParser = async {
//            array.map {
//                val obj = it.jsonObject
//                val id = obj.getPrimitive("index").long
//                obj.getArray("aya").map {
//                    val ayaObj = it.jsonObject
//
//                    Aya.Impl(
//                        ayaObj.getPrimitive("index").long,
//                        ayaObj.getPrimitive("text").content,
//                        id
//                    )
//                }
//            }.flatten()
//        }
//
//
//        val array = Json.parseJson(quranText).jsonObject.getArray("quran")
//        val soras: List<Sora.Impl> = array.map {
//            val obj = it.jsonObject
//            Sora.Impl(obj.getPrimitive("index").long, obj.getPrimitive("name").content)
//        }


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

    private fun getRandomUUID() = UUID.randomUUID().toString()
}

data class S(
    val id: SurahId,
    val orderIndex: SurahOrderId,
    val revealType: RevealTypeId,
    val revealFlag: RevealTypeFlag,
    val names: List<N>
)

data class N(
    val id: NameId,
    val rowId: RawId,
    val calligraphy: CalligraphyId,
    val content: String
)


/*
    @Test
    fun parsingAndInsertingWithoutCoroutines() {
        val testBeginningTime = System.currentTimeMillis()
        val quranText = InstrumentationRegistry.getInstrumentation().targetContext
            .resources
            .assets
            .open("quran-simple-one-line.json")
            .use {
                val bytes = ByteArray(it.available())
                it.read(bytes)
                bytes.decodeToString()
            }


        val array = Json.parseJson(quranText).jsonObject.getArray("quran")
        val soras: List<Sora.Impl> = array.map {
            val obj = it.jsonObject
            Sora.Impl(obj.getPrimitive("index").long, obj.getPrimitive("name").content)
        }

        val ayaArray: List<Aya> = array.map {
                val obj = it.jsonObject
                val id = obj.getPrimitive("index").long
                obj.getArray("aya").map {
                    val ayaObj = it.jsonObject

                    Aya.Impl(
                        ayaObj.getPrimitive("index").long,
                        ayaObj.getPrimitive("text").content,
                        id
                    )
                }
            }.flatten()


        val queries = db.soraQueries
        val transactionTime = measureTimeMillis {
            queries.transaction {
                soras.forEach {
                    queries.insertSora(it.id, it.title)
                }
            }
        }
        Log.e("test tag", "time taken to insert using transaction= $transactionTime")

        val ayaQueries = db.ayaQueries

        val ayaTransactionTime = measureTimeMillis {
            ayaQueries.transaction {
                ayaArray.forEach {
                    ayaQueries.insertAya(it.id, it.text, it.sora)
                }
            }
        }

        Log.e("test tag", "time taken to insert ayas using transaction= $ayaTransactionTime")

        val timeTakenToRunTheTest = System.currentTimeMillis() - testBeginningTime


        Log.e("test tag", "running test without coroutines=$timeTakenToRunTheTest")
    }

    @ExperimentalStdlibApi
    @Test
    fun zInsertingSoraNamesWithTransactionTest() {
        val startingToParse = System.currentTimeMillis()
        val quranText = InstrumentationRegistry.getInstrumentation().targetContext
            .resources
            .assets
            .open("quran-simple-one-line.json")
            .use {
                val bytes = ByteArray(it.available())
                it.read(bytes)
                bytes.decodeToString()
            }


        val array = Json.parseJson(quranText).jsonObject.getArray("quran")
        val soras: List<Sora.Impl> = array.map {
            val obj = it.jsonObject
            Sora.Impl(obj.getPrimitive("index").long, obj.getPrimitive("name").content)
        }

        val ayaArray: List<Aya> = array.map {
            val obj = it.jsonObject
            val id = obj.getPrimitive("index").long
            obj.getArray("aya").map {
                val ayaObj = it.jsonObject

                Aya.Impl(
                    ayaObj.getPrimitive("index").long,
                    ayaObj.getPrimitive("text").content,
                    id
                )
            }
        }
            .flatten()

        val parsingAyas = System.currentTimeMillis() - startingToParse
        Log.e("test tag", "time taken to parse all the ayas= $parsingAyas")

        val queries = db.soraQueries
        val transactionTime = measureTimeMillis {
            queries.transaction {
                soras.forEach {
                    queries.insertSora(it.id, it.title)
                }
            }
        }
        Log.e("test tag", "time taken to insert using transaction= $transactionTime")


        val insertTime = measureTimeMillis {
            soras.forEach {
                queries.insertSora(it.id, it.title)
            }
        }

        Log.e("test tag", "time taken to insert= $insertTime")

        val ayaQueries = db.ayaQueries


        val ayaTransactionTime = measureTimeMillis {
            ayaQueries.transaction {
                ayaArray.forEach {
                    ayaQueries.insertAya(it.id, it.text, it.sora)
                }
            }
        }
        Log.e("test tag", "time taken to insert ayas using transaction= $ayaTransactionTime")


        val ayaInsertTime = measureTimeMillis {
            ayaArray.forEach {
                ayaQueries.insertAya(it.id, it.text, it.sora)
            }
        }

        Log.e("test tag", "time taken to insert ayas= $ayaInsertTime")
    }

    @Test
    fun insertingSoraNamesUsingTransactionTest(){
        val startingToParse = System.currentTimeMillis()
        val quranText = InstrumentationRegistry.getInstrumentation().targetContext
            .resources
            .assets
            .open("quran-simple-one-line.json")
            .use {
                val bytes = ByteArray(it.available())
                it.read(bytes)
                bytes.decodeToString()
            }


        val array = Json.parseJson(quranText).jsonObject.getArray("quran")
        val soras: List<Sora.Impl> = array.map {
            val obj = it.jsonObject
            Sora.Impl(obj.getPrimitive("index").long, obj.getPrimitive("name").content)
        }

        val ayaArray: List<Aya> = array.map {
            val obj = it.jsonObject
            val id = obj.getPrimitive("index").long
            obj.getArray("aya").map {
                val ayaObj = it.jsonObject

                Aya.Impl(
                    ayaObj.getPrimitive("index").long,
                    ayaObj.getPrimitive("text").content,
                    id
                )
            }
        }
            .flatten()

        val parsingAyas = System.currentTimeMillis() - startingToParse
        Log.e("test tag", "time taken to parse all the ayas= $parsingAyas")

        val queries = db.soraQueries
        val transactionTime = measureTimeMillis {
            queries.transaction {
                soras.forEach {
                    queries.insertSora(it.id, it.title)
                }
            }
        }
        Log.e("test tag", "time taken to insert using transaction= $transactionTime")

    }

    @Test
    fun insertingSoraNamesUsingNormalInsertTest(){
        val startingToParse = System.currentTimeMillis()
        val quranText = InstrumentationRegistry.getInstrumentation().targetContext
            .resources
            .assets
            .open("quran-simple-one-line.json")
            .use {
                val bytes = ByteArray(it.available())
                it.read(bytes)
                bytes.decodeToString()
            }


        val array = Json.parseJson(quranText).jsonObject.getArray("quran")
        val soras: List<Sora.Impl> = array.map {
            val obj = it.jsonObject
            Sora.Impl(obj.getPrimitive("index").long, obj.getPrimitive("name").content)
        }

        val ayaArray: List<Aya> = array.map {
            val obj = it.jsonObject
            val id = obj.getPrimitive("index").long
            obj.getArray("aya").map {
                val ayaObj = it.jsonObject

                Aya.Impl(
                    ayaObj.getPrimitive("index").long,
                    ayaObj.getPrimitive("text").content,
                    id
                )
            }
        }
            .flatten()

        val parsingAyas = System.currentTimeMillis() - startingToParse
        Log.e("test tag", "time taken to parse all the ayas= $parsingAyas")


        val queries = db.soraQueries

        val insertTime = measureTimeMillis {
            soras.forEach {
                queries.insertSora(it.id, it.title)
            }
        }


        Log.e("test tag", "time taken to insert= $insertTime")

    }

    @Test
    fun insertingAyasWithTransactionTest(){
        val startingToParse = System.currentTimeMillis()
        val quranText = InstrumentationRegistry.getInstrumentation().targetContext
            .resources
            .assets
            .open("quran-simple-one-line.json")
            .use {
                val bytes = ByteArray(it.available())
                it.read(bytes)
                bytes.decodeToString()
            }


        val array = Json.parseJson(quranText).jsonObject.getArray("quran")
        val soras: List<Sora.Impl> = array.map {
            val obj = it.jsonObject
            Sora.Impl(obj.getPrimitive("index").long, obj.getPrimitive("name").content)
        }

        val ayaArray: List<Aya> = array.map {
            val obj = it.jsonObject
            val id = obj.getPrimitive("index").long
            obj.getArray("aya").map {
                val ayaObj = it.jsonObject

                Aya.Impl(
                    ayaObj.getPrimitive("index").long,
                    ayaObj.getPrimitive("text").content,
                    id
                )
            }
        }
            .flatten()

        val parsingAyas = System.currentTimeMillis() - startingToParse
        Log.e("test tag", "time taken to parse all the ayas= $parsingAyas")

        val queries = db.soraQueries
        val transactionTime = measureTimeMillis {
            queries.transaction {
                soras.forEach {
                    queries.insertSora(it.id, it.title)
                }
            }
        }

        Log.e("test tag", "time taken to insert using transaction= $transactionTime")

        val ayaQueries = db.ayaQueries


        val ayaTransactionTime = measureTimeMillis {
            ayaQueries.transaction {
                ayaArray.forEach {
                    ayaQueries.insertAya(it.id, it.text, it.sora)
                }
            }
        }
        Log.e("test tag", "time taken to insert ayas using transaction= $ayaTransactionTime")
    }


    @Test
    fun insertingAyasWithNormalInsertTest(){
        val startingToParse = System.currentTimeMillis()
        val quranText = InstrumentationRegistry.getInstrumentation().targetContext
            .resources
            .assets
            .open("quran-simple-one-line.json")
            .use {
                val bytes = ByteArray(it.available())
                it.read(bytes)
                bytes.decodeToString()
            }


        val array = Json.parseJson(quranText).jsonObject.getArray("quran")
        val soras: List<Sora.Impl> = array.map {
            val obj = it.jsonObject
            Sora.Impl(obj.getPrimitive("index").long, obj.getPrimitive("name").content)
        }

        val ayaArray: List<Aya> = array.map {
            val obj = it.jsonObject
            val id = obj.getPrimitive("index").long
            obj.getArray("aya").map {
                val ayaObj = it.jsonObject

                Aya.Impl(
                    ayaObj.getPrimitive("index").long,
                    ayaObj.getPrimitive("text").content,
                    id
                )
            }
        }
            .flatten()

        val parsingAyas = System.currentTimeMillis() - startingToParse
        Log.e("test tag", "time taken to parse all the ayas= $parsingAyas")

        val queries = db.soraQueries
        val transactionTime = measureTimeMillis {
            queries.transaction {
                soras.forEach {
                    queries.insertSora(it.id, it.title)
                }
            }
        }

        Log.e("test tag", "time taken to insert using transaction= $transactionTime")

        val ayaQueries = db.ayaQueries


        val ayaInsertTime = measureTimeMillis {
            ayaArray.forEach {
                ayaQueries.insertAya(it.id, it.text, it.sora)
            }
        }

        Log.e("test tag", "time taken to insert ayas= $ayaInsertTime")
    }
}
*/