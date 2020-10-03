package com.islamversity.daggercore.lifecycle

import kotlinx.coroutines.flow.Flow

interface LifecycleComponent {

    fun lifecycleEvents(): Flow<LifecycleEvent>
}
