package com.islamversity.daggercore.modules

import android.app.Application
import com.islamversity.daggercore.db.AppDBConfig
import com.islamversity.db.Main
import com.islamversity.domain.usecase.DatabaseFileConfig
import com.islamversity.domain.usecase.DatabaseFillerUseCase
import com.islamversity.domain.usecase.DatabaseFillerUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
object DatabaseFillerConfigModule {
    @Provides
    @JvmStatic
    fun provideDatabaseFillerConfig(app: Application): DatabaseFileConfig =
        AppDBConfig(app)

    @Provides
    @JvmStatic
    fun provideDatabaseFiller(db : Main, config : DatabaseFileConfig) : DatabaseFillerUseCase =
        DatabaseFillerUseCaseImpl(db, config)
}