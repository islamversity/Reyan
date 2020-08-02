package com.islamversity.db_test

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.islamversity.db.Aya
import com.islamversity.db.Main
import com.islamversity.db.Sora
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import kotlin.system.measureTimeMillis

/*
@ExperimentalStdlibApi
@RunWith(AndroidJUnit4::class)
class InsertQuranTextTest {

    lateinit var driver: SqlDriver
    lateinit var db: Main
    val dbName = "test"

    @Before
    fun setup() {
        driver = AndroidSqliteDriver(
            Main.Schema,
            InstrumentationRegistry.getInstrumentation().targetContext,
            dbName,
            useNoBackupDirectory = true
        )
        db = Main(driver)
    }

    @After
    fun tearDown() {
        val file = File(InstrumentationRegistry.getInstrumentation().targetContext.getNoBackupFilesDir(), dbName)
        assertThat(file.exists()).isTrue()
        driver.close()
        file.delete()
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


        val array = Json.parseJson(quranText).jsonObject.getArray("quran")
        val soras: List<Sora.Impl> = array.map {
            val obj = it.jsonObject
            Sora.Impl(obj.getPrimitive("index").long, obj.getPrimitive("name").content)
        }

        val ayaParser = async {
            array.map {
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
        }

        val queries = db.soraQueries
        val transactionTime = measureTimeMillis {
            queries.transaction {
                soras.forEach {
                    queries.insertSora(it.id, it.title)
                }
            }
        }
        Log.e("test tag", "time taken to insert using transaction= $transactionTime")

        val ayaArray: List<Aya> = ayaParser.await()
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


        Log.e("test tag", "running test with coroutines=$timeTakenToRunTheTest")
    }

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