package com.islamversity.daggercore.modules

import android.app.Application
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import com.islamversity.daggercore.sqlitehelper.SQLiteCopyOpenHelperFactory
import com.islamversity.db.*
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabaseDriver(context: Application): SqlDriver {
        val frameworkFactory = FrameworkSQLiteOpenHelperFactory()

        val callback = AndroidSqliteDriver.Callback(Main.Schema)

        val config =
            SupportSQLiteOpenHelper.Configuration.builder(context)
                .name("Main.db")
                .callback(callback)
                .build()

        val openHelper = FrameworkSQLiteOpenHelperFactory()
            .create(config)

        return AndroidSqliteDriver(
            openHelper = openHelper
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(driver: SqlDriver): Main =
        createMainDB(driver)

    @Provides
    fun provideSurahQueries(db: Main): SurahQueries =
        db.surahQueries

    @Provides
    fun provideAyaQueries(db: Main): AyaQueries =
        db.ayaQueries

    @Provides
    fun provideAyaContentQueries(db: Main): AyaContentQueries =
        db.ayaContentQueries

    @Provides
    fun provideCalligraphyQueries(db: Main): CalligraphyQueries =
        db.calligraphyQueries

    @Provides
    fun provideNameQueries(db: Main): NameQueries =
        db.nameQueries

    @Provides
    fun provideSettingsQueries(db: Main): SettingsQueries =
        db.settingsQueries
}