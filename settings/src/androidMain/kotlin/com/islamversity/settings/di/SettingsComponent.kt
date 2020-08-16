package com.islamversity.settings.di

import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.settings.SettingsView
import dagger.Component

@FeatureScope
@Component(dependencies = [CoreComponent::class], modules = [SettingsModule::class])
interface SettingsComponent {

    fun inject(view : SettingsView)

    @Component.Factory
    interface Factory{

        fun create(
            core : CoreComponent
        ) : SettingsComponent
    }
}