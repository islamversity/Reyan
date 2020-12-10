package com.islamversity.db_test.db_filler

import android.app.Application
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import co.touchlab.kermit.LogcatLogger
import com.islamversity.core.Logger
import com.islamversity.daggercore.modules.DatabaseFillerConfigModule
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
    fun fillTheDB() = runBlockingTest {

        Logger.init(listOf(LogcatLogger()))
        InstrumentationRegistry.getInstrumentation().targetContext.deleteDatabase("Main.db")
        val driver = AndroidSqliteDriver(
            Main.Schema,
            InstrumentationRegistry.getInstrumentation().targetContext,
            "Main.db",
            useNoBackupDirectory = false
        )

        val db = createMainDB(driver)

        val config =
            DatabaseFillerConfigModule.provideDatabaseFillerConfig(
                InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as Application
            )

        val useCase = DatabaseFillerUseCaseImpl(db, config)

        assert(useCase.needsFilling())

        useCase.fill()
    }

    private fun getRandomUUID() = UUID.randomUUID().toString()
}