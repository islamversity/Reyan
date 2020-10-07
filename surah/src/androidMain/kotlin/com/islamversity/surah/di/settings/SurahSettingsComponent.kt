package com.islamversity.surah.di.settings

import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.surah.view.settings.SurahSettingsView
import dagger.Component

@FeatureScope
@Component(dependencies = [CoreComponent::class], modules = [SurahSettingsModule::class])
interface SurahSettingsComponent {

    fun inject(view : SurahSettingsView)

    @Component.Factory
    interface Factory{

        fun create(
            core : CoreComponent
        ) : SurahSettingsComponent
    }
}