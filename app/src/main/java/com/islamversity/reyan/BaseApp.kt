package com.islamversity.reyan

import android.app.Application
import android.content.Context
import android.util.Log
import co.touchlab.kermit.LogcatLogger
import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate
import com.islamversity.base.widgets.AppFontSizeStore
import com.islamversity.core.Logger
import com.islamversity.reyan.BuildConfig.DEBUG
import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.CoreComponentProvider
import com.islamversity.daggercore.DaggerCoreComponent
import com.islamversity.daggercore.helpers.DEFAULT_LANGUAGE_LOCALE
import com.islamversity.daggercore.modules.DatabaseModule
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig

abstract class BaseApp : Application(), CoreComponentProvider {

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
        val config: YandexMetricaConfig = YandexMetricaConfig
            .newConfigBuilder(BuildConfig.YandexAPIKey)
            .build()
        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(applicationContext, config)

        Logger.init(listOf(LogcatLogger()))

        AppFontSizeStore.init(this)
    }
}