package com.islamversity.surah.di

import com.bluelinelabs.conductor.Router
import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.navigator.NavigatorModule
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.surah.view.SurahView
import dagger.BindsInstance
import dagger.Component

@FeatureScope
@Component(
    dependencies = [
        CoreComponent::class],
    modules = [
        SurahModule::class,
        NavigatorModule::class
    ]
)
interface SurahComponent {
    fun inject(view: SurahView)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindRouter(router: Router): Builder
        fun coreComponent(coreComponent: CoreComponent): Builder
        fun build(): SurahComponent
    }
}