package com.islamversity.reyan.di

import com.islamversity.reyan.MainActivity
import com.islamversity.daggercore.lifecycle.LifecycleComponent
import com.islamversity.daggercore.scope.ActivityScope
import dagger.Component

@ActivityScope
@Component(
    modules = [LifecycleEventsModule::class]
)
interface ActivityComponent :
    LifecycleComponent {

    fun inject(activity: MainActivity)
}
