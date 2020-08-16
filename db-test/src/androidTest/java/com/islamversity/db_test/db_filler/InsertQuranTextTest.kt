package com.islamversity.db_test.db_filler

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.islamversity.db.*
import com.islamversity.db.model.*
import com.islamversity.db.model.Calligraphy
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@ExperimentalStdlibApi
@RunWith(AndroidJUnit4::class)
class InsertQuranTextTest {

    lateinit var driver: SqlDriver
    lateinit var db: Main
    val dbName = "Main"

    @Before
    fun setup() {
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
        val quranText = InstrumentationRegistry.getInstrumentation().targetContext
            .resources
            .assets
            .open("quran-simple-one-line.json")
            .use {
                val bytes = ByteArray(it.available())
                it.read(bytes)
                bytes.decodeToString()
            }

        val calligraphyId = calligraphyArabicFiller()

        val meccaRevealId = RevealTypeId(getRandomUUID())
        val medinanRevealId = RevealTypeId(getRandomUUID())
        db.suraRevealTypeQueries.insertType(meccaRevealId, RevealTypeFlag.MECCAN)
        db.suraRevealTypeQueries.insertType(medinanRevealId, RevealTypeFlag.MEDINAN)

        val meccaArId = NameId(getRandomUUID())
        val medinanArId = NameId(getRandomUUID())
        db.nameQueries.insertName(meccaArId, meccaRevealId.raw, calligraphyId, "مكية")
        db.nameQueries.insertName(medinanArId, medinanRevealId.raw, calligraphyId, "مدنية")

        //we have 2 types of calligraphies one for "name" table
        // and one for "aya_content"
        // to find correct calligraphy for each table we can use a single left join
        // in case we mix up more data we can use 2 joins to find correct calligraphy for each table
        // calligraphy left join name left join surah (for all calligraphies for surah)
        // calligraphy left join aya_content left join aya (for all calligraphies for ayas)


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

    private fun calligraphyArabicFiller() : CalligraphyId {
        val calligraphyId = CalligraphyId(getRandomUUID())
        val languageCode = LanguageCode("ar")
        val friendlyName = "عربی"
        val calligraphy = Calligraphy(languageCode, null)

        db.calligraphyQueries.insertCalligraphy(calligraphyId, languageCode, null, friendlyName, calligraphy)
        return calligraphyId
    }

    private fun getRandomUUID() = UUID.randomUUID().toString()
}
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