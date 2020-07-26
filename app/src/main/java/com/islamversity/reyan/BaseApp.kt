package com.islamversity.reyan

import android.app.Application
import android.util.Log
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.islamversity.reyan.BuildConfig.DEBUG
import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.CoreComponentProvider
import com.islamversity.daggercore.DaggerCoreComponent
import com.islamversity.daggercore.modules.DatabaseModule

abstract class BaseApp : Application(), CoreComponentProvider {

    private lateinit var frescoConfig: ImagePipelineConfig

    protected val coreComponent by lazy {
        DaggerCoreComponent
            .builder()
            .setApplication(this)
            .setDatabaseModule(DatabaseModule())
            .build() as CoreComponent
    }

    override fun core(): CoreComponent =
        coreComponent

    override fun onCreate() {
        super.onCreate()
        frescoConfig = coreComponent.frescoConfig()

        Fresco.initialize(this, frescoConfig)

        FLog.setMinimumLoggingLevel(
            if (DEBUG)
                Log.VERBOSE
            else
                Log.ERROR
        )

        initCrashlytics()
    }

    private fun initCrashlytics() {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!DEBUG)
    }
}