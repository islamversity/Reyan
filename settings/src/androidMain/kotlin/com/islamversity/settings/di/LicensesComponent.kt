package com.islamversity.settings.di

import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.settings.LicensesView
import dagger.Component

@FeatureScope
@Component(dependencies = [CoreComponent::class], modules = [LicensesModule::class])
interface LicensesComponent {

    fun inject(view: LicensesView)

    @Component.Factory
    interface Factory {

        fun create(core: CoreComponent): LicensesComponent
    }
}