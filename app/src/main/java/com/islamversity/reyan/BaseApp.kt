package com.islamversity.reyan

import android.app.Application
import android.content.Context
import android.util.Log
import co.touchlab.kermit.LogcatLogger
import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.islamversity.base.widgets.AppFontSizeStore
import com.islamversity.core.Logger
import com.islamversity.reyan.BuildConfig.DEBUG
import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.CoreComponentProvider
import com.islamversity.daggercore.DaggerCoreComponent
import com.islamversity.daggercore.helpers.DEFAULT_LANGUAGE_LOCALE
import com.islamversity.daggercore.modules.DatabaseModule

abstract class BaseApp : Application(), CoreComponentProvider {

    private lateinit var frescoConfig: ImagePipelineConfig

    private val localizationDelegate = LocalizationApplicationDelegate()

    protected val coreComponent by lazy {
        DaggerCoreComponent
            .builder()
            .setApplication(this)
            .setDatabaseModule(DatabaseModule())
            .build() as CoreComponent
    }

    override fun core(): CoreComponent =
        coreComponent

    override fun attachBaseContext(base: Context) {
        localizationDelegate.setDefaultLanguage(base, DEFAULT_LANGUAGE_LOCALE)
        super.attachBaseContext(localizationDelegate.attachBaseContext(base))
    }

    override fun onCreate() {
        super.onCreate()
        frescoConfig = coreComponent.frescoConfig()

        Logger.init(listOf(LogcatLogger()))

        Fresco.initialize(this, frescoConfig)

        AppFontSizeStore.init(this)

        FLog.setMinimumLoggingLevel(
            if (DEBUG)
                Log.VERBOSE
            else
                Log.ERROR
        )
    }
}