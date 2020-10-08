package com.islamversity.daggercore

import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.islamversity.daggercore.modules.DatabaseModule
import com.islamversity.daggercore.modules.domain.CalligraphyModule
import com.islamversity.daggercore.modules.network.NetworkModule
import com.islamversity.daggercore.scope.AppScope
import com.islamversity.daggercore.modules.domain.DomainModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@AppScope
@Component(
    modules = [
        NetworkModule::class,
        DomainModule::class,
        CalligraphyModule::class,
        DatabaseModule::class,
        FrescoModule::class,]
)
interface CoreComponent : BaseComponent{

    fun networkFlipperPlugin(): NetworkFlipperPlugin

    @Component.Builder
    interface Builder : BaseComponent.Builder {
        override fun build(): CoreComponent
    }
}