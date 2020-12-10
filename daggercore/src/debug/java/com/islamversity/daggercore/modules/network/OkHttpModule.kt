package com.islamversity.daggercore.modules.network

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import dagger.Module
import dagger.Provides
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine
import io.ktor.util.InternalAPI
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module(includes = [InnerOkHttpModule::class])
object OkHttpModule {

    @JvmStatic
    @Provides
    fun provideChucker(app: Application) =
        ChuckerInterceptor(app)

    @JvmStatic
    @Provides
    @Singleton
    fun provideFlipperPlugin(): NetworkFlipperPlugin =
        NetworkFlipperPlugin()

    @JvmStatic
    @Provides
    @Singleton
    fun provideClient(
        flipperPlugin: NetworkFlipperPlugin,
        chucker: ChuckerInterceptor,
        @InnerOkHttpQualifier client: OkHttpClient
    ): OkHttpClient {
        return client.newBuilder()
            .addInterceptor(chucker)
            .addInterceptor(FlipperOkhttpInterceptor(flipperPlugin))
            .build()
    }

    @InternalAPI
    @JvmStatic
    @Provides
    fun provideHttpClient(okHttp : OkHttpClient): HttpClientEngine =
        OkHttpEngine(OkHttpConfig().apply {
            preconfigured = okHttp
        })
}
