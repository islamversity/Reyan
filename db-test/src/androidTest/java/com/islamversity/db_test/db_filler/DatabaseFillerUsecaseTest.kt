package com.islamversity.db_test.db_filler

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import co.touchlab.kermit.LogcatLogger
import com.islamversity.core.Logger
import com.islamversity.db.Calligraphy
import com.islamversity.db.Main
import com.islamversity.db.createMainDB
import com.islamversity.db.model.CalligraphyId
import com.islamversity.db.model.CalligraphyName
import com.islamversity.db.model.LanguageCode
import com.islamversity.domain.usecase.DatabaseFileConfig
import com.islamversity.domain.usecase.DatabaseFillerUseCaseImpl
import com.squareup.sqldelight.android.AndroidSqliteDriver
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@ExperimentalStdlibApi
@RunWith(AndroidJUnit4::class)
class DatabaseFillerUsecaseTest {

    @Test
    fun fillTheDB() = runBlockingTest{

        Logger.init(listOf(LogcatLogger()))
        InstrumentationRegistry.getInstrumentation().targetContext.deleteDatabase("Main.db")
        val driver = AndroidSqliteDriver(
            Main.Schema,
            InstrumentationRegistry.getInstrumentation().targetContext,
            "Main.db",
            useNoBackupDirectory = false
        )

        val db = createMainDB(driver)

        val config = object : DatabaseFileConfig{
            override val assetVersion: Int = 1

            override val arabicTextCalligraphy: Calligraphy =
                Calligraphy(
                0,
                    CalligraphyId(getRandomUUID()),
                    LanguageCode("ar"),
                    CalligraphyName("simple"),
                    "عربي-بسيط",
                    com.islamversity.db.model.Calligraphy(LanguageCode("ar"), CalligraphyName("simple"))
                )

            override val englishTextCalligraphy: Calligraphy =
                Calligraphy(
                    0,
                    CalligraphyId(getRandomUUID()),
                    LanguageCode("en"),
                    CalligraphyName("Abdullah Yusuf Ali"),
                    "English-Yusuf Ali",
                    com.islamversity.db.model.Calligraphy(LanguageCode("en"), CalligraphyName("Abdullah Yusuf Ali"))
                )
            override val farsiTextCalligraphy: Calligraphy =
                Calligraphy(
                    0,
                    CalligraphyId(getRandomUUID()),
                    LanguageCode("fa"),
                    CalligraphyName("Makarem Shirazi"),
                    "فارسی-مکارم شیرازی",
                    com.islamversity.db.model.Calligraphy(LanguageCode("fa"), CalligraphyName("Makarem Shirazi"))
                )

            override fun generateRandomUUID() : String =
                getRandomUUID()

            override fun getQuranArabicText(): ByteArray {
                return InstrumentationRegistry.getInstrumentation().targetContext
                    .resources
                    .assets
                    .open("quran-simple-one-line.json")
                    .use {
                        val bytes = ByteArray(it.available())
                        it.read(bytes)
                        bytes
                    }
            }

            override fun getQuranEnglishText(): ByteArray {
                return InstrumentationRegistry.getInstrumentation().targetContext
                    .resources
                    .assets
                    .open("quran-en-Abdullah-Yusuf-Ali.json")
                    .use {
                        val bytes = ByteArray(it.available())
                        it.read(bytes)
                        bytes
                    }
            }

            override fun getQuranFarsiText(): ByteArray {
                return InstrumentationRegistry.getInstrumentation().targetContext
                    .resources
                    .assets
                    .open("quran-farsi-makarem.json")
                    .use {
                        val bytes = ByteArray(it.available())
                        it.read(bytes)
                        bytes
                    }
            }

            override fun getQuranExtraData(): ByteArray {
                return InstrumentationRegistry.getInstrumentation().targetContext
                    .resources
                    .assets
                    .open("quran-data.json")
                    .use {
                        val bytes = ByteArray(it.available())
                        it.read(bytes)
                        bytes
                    }
            }

        }

        val useCase = DatabaseFillerUseCaseImpl(db, config)

        assert(useCase.needsFilling())

        useCase.fill()
    }

    private fun getRandomUUID() = UUID.randomUUID().toString()
}