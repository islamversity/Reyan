package com.islamversity.daggercore

import com.islamversity.daggercore.modules.DatabaseModule
import com.islamversity.daggercore.modules.domain.DomainModule
import com.islamversity.daggercore.modules.network.NetworkModule
import com.islamversity.daggercore.scope.AppScope
import dagger.Component
import javax.inject.Singleton

@Singleton
@AppScope
@Component(
    modules = [
        NetworkModule::class,
        DomainModule::class,
        DatabaseModule::class,
        FrescoModule::class]
)
interface CoreComponent : BaseComponent{

    @Component.Builder
    interface Builder : BaseComponent.Builder {

        override fun build(): CoreComponent
    }
}