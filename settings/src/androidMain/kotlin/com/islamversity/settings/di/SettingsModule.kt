package com.islamversity.settings.di

import com.islamversity.core.Mapper
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.domain.model.Calligraphy
import com.islamversity.domain.repo.CalligraphyRepo
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.settings.models.CalligraphyDomainUIMapper
import com.islamversity.settings.models.CalligraphyUIModel
import com.islamversity.settings.settings.*
import dagger.Module
import dagger.Provides
import com.islamversity.navigation.Navigator


@Module
object SettingsModule {
    @JvmStatic
    @Provides
    @FeatureScope
    fun providePresenter(
        processor: MviProcessor<SettingsIntent, SettingsResult>
    ): MviPresenter<SettingsIntent, SettingsState> = SettingsPresenter(processor)

    @Provides
    @JvmStatic
    fun provideCalligraphyDomainUIMapper(): Mapper<Calligraphy, CalligraphyUIModel> =
        CalligraphyDomainUIMapper()

    @JvmStatic
    @Provides
    fun provideProcessor(
        settingsRepo: SettingRepo,
        calligraphyRepo: CalligraphyRepo,
        mapper : Mapper<Calligraphy, CalligraphyUIModel>,
        navigator : Navigator
    ): MviProcessor<SettingsIntent, SettingsResult> =
        SettingsProcessor(settingsRepo, calligraphyRepo, mapper, navigator)

}