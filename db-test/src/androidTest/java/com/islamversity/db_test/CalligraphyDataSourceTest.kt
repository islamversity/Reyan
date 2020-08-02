package com.islamversity.db_test

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.google.common.truth.Truth
import com.islamversity.db.*
import com.islamversity.db.Aya
import com.islamversity.db.Calligraphy
import com.islamversity.db.Sora
import com.islamversity.db.datasource.CalligraphyLocalDataSourceImpl
import com.islamversity.db.model.*
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.time.ExperimentalTime

@ExperimentalTime
@RunWith(AndroidJUnit4::class)
class CalligraphyDataSourceTest {

    val dispatcher = TestCoroutineDispatcher()

    lateinit var dataSource: CalligraphyLocalDataSourceImpl

    lateinit var driver: SqlDriver
    lateinit var db: Main
    val dbName = "test"

    @Before
    fun setup() {
        driver = AndroidSqliteDriver(
            Main.Schema,
            InstrumentationRegistry.getInstrumentation().targetContext,
            dbName
        )
        val calligraphyAdapter =
            Calligraphy.Adapter(CalligraphyIdAdapter(), LanguageCodeAdapter(), CalligraphyNameAdapter(), CalligraphyAdapter())
        val nameAdapter = Name.Adapter(NameIdAdapter(), RawIdAdapter(), CalligraphyIdAdapter())
        val soraAdapter = Sora.Adapter(SoraIdAdapter())
        val ayaAdapter = Aya.Adapter(AyaIdAdapter(), SoraIdAdapter())
        val ayaContentAdapter = Aya_content.Adapter(AyaContentIdAdapter(), AyaIdAdapter(), CalligraphyIdAdapter())


        db = Main(driver, calligraphyAdapter, nameAdapter, soraAdapter, ayaAdapter, ayaContentAdapter)

        dataSource = CalligraphyLocalDataSourceImpl(db.calligraphyQueries)
    }

    @After
    fun tearDown() {
        InstrumentationRegistry.getInstrumentation().targetContext.deleteDatabase(dbName)
    }

    @Test
    fun successfullyInsertsCalligraphiesIntoTheDB() = runBlockingTest(dispatcher) {
        val id = CalligraphyId(UUID.randomUUID().toString())
        val lang = LanguageCode("ar")
        val name = CalligraphyName("nastaligh")
        val friendlyName = "عربی نستعلیغ"
        val code = com.islamversity.db.model.Calligraphy(lang, name)

        dataSource.insertCalligraphy(id, lang, name, friendlyName, code, dispatcher)

        dataSource.observeAllCallygraphies(dispatcher).test {
            val list = expectItem()
            val first = list.first()

            Truth.assertThat(first.id).isEqualTo(id)
            Truth.assertThat(first.languageCode).isEqualTo(lang)
            Truth.assertThat(first.name).isEqualTo(name)
            Truth.assertThat(first.friendlyName).isEqualTo(friendlyName)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun successfullyFindCalligraphyByCode() = runBlockingTest(dispatcher) {
        val id = CalligraphyId(UUID.randomUUID().toString())
        val lang = LanguageCode("ar")
        val name = CalligraphyName("nastaligh")
        val friendlyName = "عربی نستعلیغ"
        val code = com.islamversity.db.model.Calligraphy(lang, name)

        dataSource.insertCalligraphy(id, lang, name, friendlyName, code, dispatcher)

        dataSource.getCalligraphyByCode(code, dispatcher).test {
            val item = expectItem()!!

            Truth.assertThat(item.id).isEqualTo(id)
            Truth.assertThat(item.languageCode).isEqualTo(lang)
            Truth.assertThat(item.name).isEqualTo(name)
            Truth.assertThat(item.friendlyName).isEqualTo(friendlyName)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun successfullyFindCalligraphyById() = runBlockingTest(dispatcher) {
        val id = CalligraphyId(UUID.randomUUID().toString())
        val lang = LanguageCode("ar")
        val name = CalligraphyName("nastaligh")
        val friendlyName = "عربی نستعلیغ"
        val code = com.islamversity.db.model.Calligraphy(lang, name)

        dataSource.insertCalligraphy(id, lang, name, friendlyName, code, dispatcher)

        dataSource.getCalligraphyById(id, dispatcher).test {
            val item = expectItem()!!

            Truth.assertThat(item.id).isEqualTo(id)
            Truth.assertThat(item.languageCode).isEqualTo(lang)
            Truth.assertThat(item.name).isEqualTo(name)
            Truth.assertThat(item.friendlyName).isEqualTo(friendlyName)

            cancelAndIgnoreRemainingEvents()
        }
    }


}