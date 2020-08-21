package com.islamversity.db_test

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.islamversity.db.*
import com.islamversity.db.datasource.SurahLocalDataSourceImpl
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
/*
@ExperimentalTime
@RunWith(AndroidJUnit4::class)
class SurahLocalDataSourceTest {

    val dispatcher = TestCoroutineDispatcher()

    lateinit var dataSource: SurahLocalDataSourceImpl

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
        val soraAdapter = Surah.Adapter(SurahIdAdapter(), SurahRevealTypeIdAdapter())
        val ayaAdapter = Aya.Adapter(AyaIdAdapter(), SurahIdAdapter())
        val ayaContentAdapter = Aya_content.Adapter(AyaContentIdAdapter(), AyaIdAdapter(), CalligraphyIdAdapter())
        val surahTypeAdapter = SurahRevealType.Adapter(SurahRevealTypeIdAdapter(), RevealTypeFlagAdapter())

        db = Main(driver, calligraphyAdapter, nameAdapter, soraAdapter, surahTypeAdapter, ayaAdapter, ayaContentAdapter)

        dataSource = SurahLocalDataSourceImpl(db.surahQueries, db.nameQueries)
    }

    @After
    fun tearDown() {
        InstrumentationRegistry.getInstrumentation().targetContext.deleteDatabase(dbName)
    }

    @Test
    fun successfullyInsertsIntoTheDB() = runBlockingTest(dispatcher) {
        val revealTypeId = SurahRevealTypeId(UUID.randomUUID().toString())
        val revealTypeFlag = RevealTypeFlag.MECCAN
        db.suraRevealTypeQueries.insertType(revealTypeId, revealTypeFlag)


        val calligraphyId = CalligraphyId(UUID.randomUUID().toString())
        val lang = LanguageCode("ar")
        val calligraphyName = CalligraphyName("nastaligh")
        val friendlyName = "عربی نستعلیغ"
        val code = com.islamversity.db.model.Calligraphy(lang, calligraphyName)
        db.calligraphyQueries.insertCalligraphy(calligraphyId, lang, calligraphyName, friendlyName, code)

        val order = 1L
        val id = SurahId(UUID.randomUUID().toString())
        val nameId = NameId(UUID.randomUUID().toString())
        val content = "آیه اول"
        val name = No_rowId_name.Impl(nameId, id.raw, calligraphyId, content)
        val sora = SoraWithFullName(id, order, name, revealTypeId)


        dataSource.insertSurah(sora, dispatcher)

        dataSource.observeAllSurahs(code, dispatcher).test {
            val list = expectItem()
            val first = list.first()

            assertThat(first.id).isEqualTo(id)
            assertThat(first.name).isEqualTo(content)
            assertThat(first.order).isEqualTo(order)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun successfullyFindsWithId() = runBlockingTest(dispatcher) {
        val revealTypeId = SurahRevealTypeId(UUID.randomUUID().toString())
        val revealTypeFlag = RevealTypeFlag.MECCAN
        db.suraRevealTypeQueries.insertType(revealTypeId, revealTypeFlag)

        val calligraphyId = CalligraphyId(UUID.randomUUID().toString())
        val lang = LanguageCode("fa")
        val calligraphyName = CalligraphyName("simple")
        val friendlyName = "فارسی روان"
        val code = com.islamversity.db.model.Calligraphy(lang, calligraphyName)
        db.calligraphyQueries.insertCalligraphy(calligraphyId, lang, calligraphyName, friendlyName, code)

        //second
        val order = 2L
        val id = SurahId(UUID.randomUUID().toString())
        val nameId = NameId(UUID.randomUUID().toString())
        val content = "سوره دوم"
        val name = No_rowId_name.Impl(nameId, id.raw, calligraphyId, content)
        val sora = SoraWithFullName(id, order, name, revealTypeId)
        dataSource.insertSurah(sora, dispatcher)

        dataSource.getSurahWithId(id, code, dispatcher).test {
//            val error = expectError()
            val item = expectItem()!!

            assertThat(item.id).isEqualTo(id)
            assertThat(item.name).isEqualTo(content)
            assertThat(item.order).isEqualTo(order)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun successfullyFindsWithOrderAndCalligraphy() = runBlockingTest(dispatcher) {
        val revealTypeId = SurahRevealTypeId(UUID.randomUUID().toString())
        val revealTypeFlag = RevealTypeFlag.MECCAN
        db.suraRevealTypeQueries.insertType(revealTypeId, revealTypeFlag)

        val calligraphyId = CalligraphyId(UUID.randomUUID().toString())
        val lang = LanguageCode("fa")
        val calligraphyName = CalligraphyName("ravan")
        val friendlyName = "فارسی روان"
        val code = com.islamversity.db.model.Calligraphy(lang, calligraphyName)
        db.calligraphyQueries.insertCalligraphy(calligraphyId, lang, calligraphyName, friendlyName, code)

        val order = 1L
        val id = SurahId(UUID.randomUUID().toString())
        val nameId = NameId(UUID.randomUUID().toString())
        val content = "سوره دوم"
        val name = No_rowId_name.Impl(nameId, id.raw, calligraphyId, content)
        val sora = SoraWithFullName(id, order, name, revealTypeId)
        dataSource.insertSurah(sora, dispatcher)

        dataSource.getSurahWithOrderAndCalligraphy(order, code, dispatcher).test {
            val item = expectItem()!!

            assertThat(item.id).isEqualTo(id)
            assertThat(item.name).isEqualTo(content)
            assertThat(item.order).isEqualTo(order)

            cancelAndIgnoreRemainingEvents()
        }
    }
}

 */