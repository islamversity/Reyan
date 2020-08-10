package com.islamversity.search.di

import com.bluelinelabs.conductor.Router
import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.navigator.NavigatorModule
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.search.view.SearchView
import dagger.BindsInstance
import dagger.Component

@FeatureScope
@Component(
    dependencies = [
        CoreComponent::class],
    modules = [
        SearchModule::class,
        NavigatorModule::class
    ]
)
interface SearchComponent {
    fun inject(view: SearchView)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindRouter(router: Router): Builder
        fun coreComponent(coreComponent: CoreComponent): Builder
        fun build(): SearchComponent
    }
}