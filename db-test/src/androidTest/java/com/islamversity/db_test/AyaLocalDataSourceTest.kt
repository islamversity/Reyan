package com.islamversity.db_test

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.google.common.truth.Truth
import com.islamversity.db.*
import com.islamversity.db.datasource.AyaLocalDataSourceImpl
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
class AyaLocalDataSourceTest {

    val dispatcher = TestCoroutineDispatcher()

    lateinit var dataSource: AyaLocalDataSourceImpl
    lateinit var soraDS: SurahLocalDataSourceImpl

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

        dataSource = AyaLocalDataSourceImpl(db.ayaQueries, db.ayaContentQueries)
        soraDS = SurahLocalDataSourceImpl(db.surahQueries, db.nameQueries)
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
        val content = "سوره اول"
        val name = No_rowId_name.Impl(nameId, id.raw, calligraphyId, content)
        val sora = SoraWithFullName(id, order, name, revealTypeId)
        soraDS.insertSurah(sora, dispatcher)

        val ayaOrder = 1L
        val ayaId = AyaId(UUID.randomUUID().toString())
        val ayaContentId = AyaContentId(UUID.randomUUID().toString())
        val ayaContent = "آیه اول"
        val ayaName = No_rowId_aya_content.Impl(ayaContentId, ayaId, calligraphyId, ayaContent)
        val aya = AyaWithFullContent(ayaId, id, ayaOrder, ayaName)
        dataSource.insertAya(aya, dispatcher)



        dataSource.observeAllAyasForSora(id, code, dispatcher).test {
            val list = expectItem()
            val first = list.first()

            Truth.assertThat(first.id).isEqualTo(ayaId)
            Truth.assertThat(first.content).isEqualTo(ayaContent)
            Truth.assertThat(first.order).isEqualTo(ayaOrder)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun successfullyFindsWithId() = runBlockingTest(dispatcher) {
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
        val content = "سوره اول"
        val name = No_rowId_name.Impl(nameId, id.raw, calligraphyId, content)
        val sora = SoraWithFullName(id, order, name, revealTypeId)
        soraDS.insertSurah(sora, dispatcher)

        val ayaOrder = 1L
        val ayaId = AyaId(UUID.randomUUID().toString())
        val ayaContentId = AyaContentId(UUID.randomUUID().toString())
        val ayaContent = "آیه اول"
        val ayaName = No_rowId_aya_content.Impl(ayaContentId, ayaId, calligraphyId, ayaContent)
        val aya = AyaWithFullContent(ayaId, id, ayaOrder, ayaName)
        dataSource.insertAya(aya, dispatcher)


        dataSource.getAyaWithId(ayaId, code, dispatcher).test {
            val first = expectItem()!!

            Truth.assertThat(first.id).isEqualTo(ayaId)
            Truth.assertThat(first.content).isEqualTo(ayaContent)
            Truth.assertThat(first.order).isEqualTo(ayaOrder)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun successfullyFindsWithOrderAndCalligraphy(): Unit = runBlocking {
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
        val content = "سوره اول"
        val name = No_rowId_name.Impl(nameId, id.raw, calligraphyId, content)
        val sora = SoraWithFullName(id, order, name, revealTypeId)
        soraDS.insertSurah(sora, dispatcher)

        val ayaOrder = 1L
        val ayaId = AyaId(UUID.randomUUID().toString())
        val ayaContentId = AyaContentId(UUID.randomUUID().toString())
        val ayaContent = "آیه اول"
        val ayaName = No_rowId_aya_content.Impl(ayaContentId, ayaId, calligraphyId, ayaContent)
        val aya = AyaWithFullContent(ayaId, id, ayaOrder, ayaName)
        dataSource.insertAya(aya, dispatcher)

        dataSource.observeAllAyasForSora(order, code, dispatcher).test {
            val list = expectItem()
            val first = list.first()

            Truth.assertThat(first.id).isEqualTo(ayaId)
            Truth.assertThat(first.content).isEqualTo(ayaContent)
            Truth.assertThat(first.order).isEqualTo(ayaOrder)

            cancelAndIgnoreRemainingEvents()
        }
    }
}

*/