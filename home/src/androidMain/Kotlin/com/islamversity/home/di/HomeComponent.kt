package com.islamversity.home.di

import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.lifecycle.LifecycleComponent
import com.islamversity.daggercore.navigator.DefaultNavigationComponent
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.home.HomePresenter
import com.islamversity.home.view.HomeView
import dagger.Component

@FeatureScope
@Component(
    dependencies = [
        CoreComponent::class,
        LifecycleComponent::class,
        DefaultNavigationComponent::class],
    modules = [
        HomeModule::class]
)
interface HomeComponent {
    fun inject(view: HomeView)

    @Component.Builder
    interface Builder {

        fun coreComponent(coreComponent: CoreComponent): Builder

        fun lifecycleComponent(activityComponent: LifecycleComponent): Builder

        fun navComponent(navigationComponent: DefaultNavigationComponent): Builder
        fun build(): HomeComponent
    }
}
