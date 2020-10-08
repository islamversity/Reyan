package com.islamversity.daggercore

import android.app.Application
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.islamversity.daggercore.modules.DatabaseModule
import com.islamversity.domain.repo.CalligraphyRepo
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.domain.repo.aya.AyaListRepo
import com.islamversity.domain.repo.aya.GetAyaUseCase
import com.islamversity.domain.repo.juz.JuzListRepo
import com.islamversity.domain.repo.juz.JuzListUsecase
import com.islamversity.domain.repo.surah.GetSurahUsecase
import com.islamversity.domain.repo.surah.SearchSurahNameUseCase
import com.islamversity.domain.repo.surah.SurahRepo
import com.islamversity.domain.repo.surah.SurahSearchRepo
import dagger.BindsInstance
import io.ktor.client.engine.HttpClientEngine
import okhttp3.OkHttpClient

interface BaseComponent {

    fun application(): Application

    fun okHttp(): OkHttpClient
    fun httpClient(): HttpClientEngine
    fun frescoConfig(): ImagePipelineConfig

    fun provideCalligraphyRepo(): CalligraphyRepo

    fun surahListRepo(): SurahRepo

    fun provideJuzListRepo(): JuzListRepo
    fun provideJuzListUsecase(): JuzListUsecase
    fun provideGetSurahUseCase(): GetSurahUsecase
    fun surahSearchRepo(): SurahSearchRepo
    fun surahSearchUseCase(): SearchSurahNameUseCase

    fun settingRepo(): SettingRepo

    fun ayaListRepo(): AyaListRepo
    fun getAyaUsecase(): GetAyaUseCase

    interface Builder {

        @BindsInstance
        fun setApplication(app: Application): Builder

        fun setDatabaseModule(module: DatabaseModule): Builder

        fun build(): BaseComponent
    }
}