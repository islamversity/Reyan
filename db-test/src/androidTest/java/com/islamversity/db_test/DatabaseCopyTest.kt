package com.islamversity.db_test

import android.util.Log
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.islamversity.daggercore.sqlitehelper.SQLiteCopyOpenHelperFactory
import com.islamversity.db.Main
import com.islamversity.db.createMainDB
import com.islamversity.db.model.BismillahTypeFlag
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalStdlibApi
@RunWith(AndroidJUnit4::class)
class DatabaseCopyTest {

    lateinit var driver: SqlDriver
    lateinit var db: Main
    val dbName = "Main.db"

    @Before
    fun setup() {
        val file =
            InstrumentationRegistry.getInstrumentation().targetContext.getDatabasePath(dbName)

        file.delete()
    }

    @Test
    fun createAndCopyDatabase() {
        val creationIsStarted = System.currentTimeMillis()

        val frameworkFactory = FrameworkSQLiteOpenHelperFactory()

        val callback = AndroidSqliteDriver.Callback(Main.Schema)

        val config =
            SupportSQLiteOpenHelper.Configuration.builder(InstrumentationRegistry.getInstrumentation().targetContext)
                .name(dbName)
                .callback(callback)
                .build()

        val openHelper = SQLiteCopyOpenHelperFactory(
            "Main.db.zip",
            null,
            null,
            frameworkFactory
        ).create(config)

        driver = AndroidSqliteDriver(
            openHelper = openHelper
        )

        db = createMainDB(driver)

        val timeTakenToCreateObject = System.currentTimeMillis() - creationIsStarted

        Log.d(
            "database copy",
            "time taken to create an instance of the DB= $timeTakenToCreateObject"
        )


        val timeTakenToCreateAndInsertOneRow = System.currentTimeMillis() - creationIsStarted


        Log.d(
            "database copy",
            "total time taken to copy and insert in the DB= $timeTakenToCreateAndInsertOneRow"
        )
    }

}