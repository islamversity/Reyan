package com.islamversity.quran_home.feature.home.di

import com.bluelinelabs.conductor.Router
import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.lifecycle.LifecycleComponent
import com.islamversity.daggercore.navigator.DefaultNavigationComponent
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.quran_home.feature.home.QuranHomeView
import dagger.BindsInstance
import dagger.Component

@FeatureScope
@Component(
    dependencies = [
        CoreComponent::class,
        LifecycleComponent::class,
        DefaultNavigationComponent::class],
    modules = [
        QuranHomeModule::class,
        MapperModule::class]
)
interface QuranHomeComponent {
    fun inject(view: QuranHomeView)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindRouter(router: Router): Builder

        fun coreComponent(coreComponent: CoreComponent): Builder

        fun lifecycleComponent(activityComponent: LifecycleComponent): Builder

        fun navComponent(navigationComponent: DefaultNavigationComponent): Builder
        fun build(): QuranHomeComponent
    }
}
