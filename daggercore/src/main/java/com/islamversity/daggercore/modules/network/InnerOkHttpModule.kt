package com.islamversity.daggercore.modules.network

import android.app.Application
import com.islamversity.core.Logger
import com.islamversity.core.Severity
import com.islamversity.daggercore.BuildConfig.DEBUG
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object InnerOkHttpModule {

    @JvmStatic
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Logger.log(message, Severity.Info, tag= "OkHttpRequest")
            }
        })

        httpLoggingInterceptor.level = if (DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        return httpLoggingInterceptor
    }

    @JvmStatic
    @Provides
    fun provideOkhttpCache(app: Application): Cache =
        Cache(app.cacheDir, 50_000_000)

    @JvmStatic
    @Provides
    @Singleton
    @InnerOkHttpQualifier
    fun provideClient(
        loggingInterceptor: HttpLoggingInterceptor,
        protocolInterceptor: NoContentProtocolExceptionInterceptor,
        cache: Cache
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)// Set connection timeout
            .readTimeout(120, TimeUnit.SECONDS)// Read timeout
            .writeTimeout(120, TimeUnit.SECONDS)// Write timeout
//            .cache(cache)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(protocolInterceptor)
//            .addInterceptor(authInterceptor)
            .build()
    }

}