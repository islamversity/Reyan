package com.islamversity.daggercore.modules.network

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine
import io.ktor.util.InternalAPI
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module(includes = [InnerOkHttpModule::class])
abstract class OkHttpModule {

    @Binds
    @Singleton
    abstract fun bindOkHttp(@InnerOkHttpQualifier client: OkHttpClient):
            OkHttpClient


    @Module
    companion object {

        @InternalAPI
        @JvmStatic
        @Provides
        fun provideHttpClient(okHttp: OkHttpClient): HttpClientEngine =
            OkHttpEngine(OkHttpConfig().apply {
                preconfigured = okHttp
            })
    }
}
