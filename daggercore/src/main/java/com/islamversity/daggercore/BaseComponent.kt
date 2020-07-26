package com.islamversity.daggercore

import android.app.Application
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.islamversity.daggercore.modules.DatabaseModule
import dagger.BindsInstance
import io.ktor.client.engine.HttpClientEngine
import okhttp3.OkHttpClient

interface BaseComponent {

    fun application() : Application

    fun okHttp(): OkHttpClient
    fun httpClient() : HttpClientEngine

    fun frescoConfig(): ImagePipelineConfig

    interface Builder {

        @BindsInstance
        fun setApplication(app: Application): Builder

        fun setDatabaseModule(module: DatabaseModule): Builder

        fun build(): BaseComponent
    }
}