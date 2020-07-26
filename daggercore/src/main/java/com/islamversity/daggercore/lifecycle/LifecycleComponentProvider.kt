package com.islamversity.daggercore.lifecycle

import com.islamversity.daggercore.lifecycle.LifecycleComponent

interface LifecycleComponentProvider {

    fun lifecycleComponent() : LifecycleComponent
}