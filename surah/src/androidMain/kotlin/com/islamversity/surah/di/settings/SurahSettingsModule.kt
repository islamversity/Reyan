package com.islamversity.surah.di.settings

import com.islamversity.core.Mapper
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.domain.model.Calligraphy
import com.islamversity.domain.repo.CalligraphyRepo
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.surah.model.CalligraphyDomainUIMapper
import com.islamversity.surah.model.CalligraphyUIModel
import com.islamversity.surah.settings.SurahSettingsIntent
import com.islamversity.surah.settings.SurahSettingsPresenter
import com.islamversity.surah.settings.SurahSettingsProcessor
import com.islamversity.surah.settings.SurahSettingsResult
import dagger.Module
import dagger.Provides

@Module
object SurahSettingsModule {
    @JvmStatic
    @Provides
    @FeatureScope
    fun providePresenter(
        processor: MviProcessor<SurahSettingsIntent, SurahSettingsResult>
    ): SurahSettingsPresenter = SurahSettingsPresenter(processor)

    @Provides
    @JvmStatic
    fun provideCalligraphyDomainUIMapper(): Mapper<Calligraphy, CalligraphyUIModel> =
        CalligraphyDomainUIMapper()

    @JvmStatic
    @Provides
    fun provideProcessor(
        settingsRepo: SettingRepo,
        calligraphyRepo: CalligraphyRepo,
        mapper : Mapper<Calligraphy, CalligraphyUIModel>
    ): MviProcessor<SurahSettingsIntent, SurahSettingsResult> =
        SurahSettingsProcessor(settingsRepo, calligraphyRepo, mapper)

}