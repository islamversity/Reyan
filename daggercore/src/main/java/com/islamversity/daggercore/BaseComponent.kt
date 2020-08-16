package com.islamversity.daggercore

import android.app.Application
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.islamversity.daggercore.modules.DatabaseModule
import com.islamversity.domain.repo.juz.JuzListRepo
import com.islamversity.domain.repo.surah.GetSurahsUsecase
import com.islamversity.domain.repo.surah.SurahListRepo
import com.islamversity.domain.repo.CalligraphyRepo
import com.islamversity.domain.repo.SettingRepo
import dagger.BindsInstance
import io.ktor.client.engine.HttpClientEngine
import okhttp3.OkHttpClient

interface BaseComponent {

    fun application() : Application

    fun okHttp(): OkHttpClient
    fun httpClient() : HttpClientEngine

    fun frescoConfig(): ImagePipelineConfig

    fun provideCalligraphyRepo(): CalligraphyRepo
    fun provideSettingsRepo() : SettingRepo

    fun surahListRepo(): SurahListRepo

    fun provideJuzListRepo() : JuzListRepo
    fun provideGetSurahUseCase() : GetSurahsUsecase

    interface Builder {

        @BindsInstance
        fun setApplication(app: Application): Builder

        fun setDatabaseModule(module: DatabaseModule): Builder

        fun build(): BaseComponent
    }
}