package com.islamversity.settings.di

import com.bluelinelabs.conductor.Router
import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.lifecycle.LifecycleComponent
import com.islamversity.daggercore.navigator.DefaultNavigationComponent
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.settings.SettingsView
import dagger.Component
import dagger.BindsInstance

@FeatureScope
@Component(dependencies = [
    CoreComponent::class,
    LifecycleComponent::class,
    DefaultNavigationComponent::class],
    modules = [SettingsModule::class])
interface SettingsComponent {

    fun inject(view : SettingsView)

    @Component.Factory
    interface Factory {

        fun create(
            core : CoreComponent,
            @BindsInstance router: Router,
            activityComponent: LifecycleComponent,
            avigationComponent: DefaultNavigationComponent
        ) : SettingsComponent
    }
}