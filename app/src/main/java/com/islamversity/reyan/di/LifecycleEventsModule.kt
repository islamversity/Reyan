package com.islamversity.reyan.di

import com.islamversity.daggercore.lifecycle.LifecycleEvent
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@Module
object LifecycleEventsModule {

    @JvmStatic
    @Provides
    fun provideEventSubject() =
        Channel<LifecycleEvent>()

    @JvmStatic
    @Provides
    fun provideEventObservable(events: Channel<LifecycleEvent>) =
        events.receiveAsFlow()
}
