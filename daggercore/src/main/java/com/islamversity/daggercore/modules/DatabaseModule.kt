package com.islamversity.daggercore.modules

import android.app.Application
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

//    @Singleton
//    @Provides
//    fun provideDatabaseDriver(context: Application) : SqlDriver =
//        AndroidSqliteDriver(Schema, context, "main")
}