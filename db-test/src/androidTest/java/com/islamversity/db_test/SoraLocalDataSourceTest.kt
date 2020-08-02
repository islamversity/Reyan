package com.islamversity.db_test

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.islamversity.db.*
import com.islamversity.db.Aya
import com.islamversity.db.Calligraphy
import com.islamversity.db.Sora
import com.islamversity.db.datasource.SoraLocalDataSourceImpl
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
class SoraLocalDataSourceTest {


    val dispatcher = TestCoroutineDispatcher()

    lateinit var dataSource: SoraLocalDataSourceImpl

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

        dataSource = SoraLocalDataSourceImpl(db.soraQueries, db.nameQueries)
    }

    @After
    fun tearDown() {
        InstrumentationRegistry.getInstrumentation().targetContext.deleteDatabase(dbName)
    }

    @Test
    fun successfullyInsertsIntoTheDB() = runBlockingTest(dispatcher) {
        val order = 1L
        val id = SoraId(UUID.randomUUID().toString())

        val calligraphyId = CalligraphyId(UUID.randomUUID().toString())
        val lang = LanguageCode("ar")
        val calligraphyName = CalligraphyName("nastaligh")
        val friendlyName = "عربی نستعلیغ"
        val code = com.islamversity.db.model.Calligraphy(lang, calligraphyName)

        val nameId = NameId(UUID.randomUUID().toString())
        val content = "آیه اول"
        val name = No_rowId_name.Impl(nameId, id.raw, calligraphyId, content)
        val sora = SoraWithFullName(id, order, name)

        db.calligraphyQueries.insertCalligraphy(calligraphyId, lang, calligraphyName, friendlyName, code)

        dataSource.insertSora(sora, dispatcher)

        dataSource.observeAllSoras(code, dispatcher).test {
            val list = expectItem()
            val first = list.first()

            Truth.assertThat(first.id).isEqualTo(id)
            Truth.assertThat(first.name).isEqualTo(content)
            Truth.assertThat(first.order).isEqualTo(order)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun successfullyFindsWithId() = runBlockingTest(dispatcher) {
        //first
//        val firstOrder = 1L
//        val firstId = SoraId(UUID.randomUUID().toString())
//
//        val firstCalligraphyId = CalligraphyId(UUID.randomUUID().toString())
//        val firstLang = LanguageCode("ar")
//        val firstCalligraphyName = CalligraphyName("nastaligh")
//        val firstFriendlyName = "عربی نستعلیغ"
//        val firstCode = com.islamversity.db.model.Calligraphy(firstLang, firstCalligraphyName)
//
//        val firstNameId = NameId(UUID.randomUUID().toString())
//        val firstContent = "سوره اول"
//        val firstName = No_rowId_name.Impl(firstNameId, firstId.raw, firstCalligraphyId, firstContent)
//        val firstSora = SoraWithFullName(firstId, firstOrder, firstName)
//
//        db.calligraphyQueries.insertCalligraphy(firstCalligraphyId, firstLang, firstCalligraphyName, firstFriendlyName, firstCode)
//
//        dataSource.insertSora(firstSora, dispatcher)

        //second
        val order = 2L
        val id = SoraId(UUID.randomUUID().toString())

        val calligraphyId = CalligraphyId(UUID.randomUUID().toString())
        val lang = LanguageCode("fa")
        val calligraphyName = CalligraphyName("simple")
        val friendlyName = "فارسی روان"
        val code = com.islamversity.db.model.Calligraphy(lang, calligraphyName)

        val nameId = NameId(UUID.randomUUID().toString())
        val content = "سوره دوم"
        val name = No_rowId_name.Impl(nameId, id.raw, calligraphyId, content)
        val sora = SoraWithFullName(id, order, name)

        db.calligraphyQueries.insertCalligraphy(calligraphyId, lang, calligraphyName, friendlyName, code)

        dataSource.insertSora(sora, dispatcher)

        dataSource.getSoraWithId(id, dispatcher).test {
//            val error = expectError()
            val item = expectItem()!!

            Truth.assertThat(item.id).isEqualTo(id)
            Truth.assertThat(item.name).isEqualTo(content)
            Truth.assertThat(item.order).isEqualTo(order)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun successfullyFindsWithOrderAndCalligraphy() = runBlockingTest(dispatcher) {
        //first
//        val firstOrder = 1L
//        val firstId = SoraId(UUID.randomUUID().toString())
//
//        val firstCalligraphyId = CalligraphyId(UUID.randomUUID().toString())
//        val firstLang = LanguageCode("ar")
//        val firstCalligraphyName = CalligraphyName("nastaligh")
//        val firstFriendlyName = "عربی نستعلیغ"
//        val firstCode = com.islamversity.db.model.Calligraphy(firstLang, firstCalligraphyName)
//
//        val firstNameId = NameId(UUID.randomUUID().toString())
//        val firstContent = "سوره اول"
//        val firstName = No_rowId_name.Impl(firstNameId, firstId.raw, firstCalligraphyId, firstContent)
//        val firstSora = SoraWithFullName(firstId, firstOrder, firstName)
//
//        db.calligraphyQueries.insertCalligraphy(firstCalligraphyId, firstLang, firstCalligraphyName, firstFriendlyName, firstCode)
//
//        dataSource.insertSora(firstSora, dispatcher)

        //second
        val order = 1L
        val id = SoraId(UUID.randomUUID().toString())

        val calligraphyId = CalligraphyId(UUID.randomUUID().toString())
        val lang = LanguageCode("fa")
        val calligraphyName = CalligraphyName("ravan")
        val friendlyName = "فارسی روان"
        val code = com.islamversity.db.model.Calligraphy(lang, calligraphyName)

        val nameId = NameId(UUID.randomUUID().toString())
        val content = "سوره دوم"
        val name = No_rowId_name.Impl(nameId, id.raw, calligraphyId, content)
        val sora = SoraWithFullName(id, order, name)

        db.calligraphyQueries.insertCalligraphy(calligraphyId, lang, calligraphyName, friendlyName, code)

        dataSource.insertSora(sora, dispatcher)

        dataSource.getSoraWithOrderAndCalligraphy(order, code, dispatcher).test {
            val item = expectItem()!!

            assertThat(item.id).isEqualTo(id)
            assertThat(item.name).isEqualTo(content)
            assertThat(item.order).isEqualTo(order)

            cancelAndIgnoreRemainingEvents()
        }
    }
}