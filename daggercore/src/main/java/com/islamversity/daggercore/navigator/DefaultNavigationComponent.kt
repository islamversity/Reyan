package com.islamversity.daggercore.navigator

import com.bluelinelabs.conductor.Router
import com.islamversity.daggercore.CoreComponent
import com.islamversity.navigation.Navigator
import dagger.BindsInstance
import dagger.Component

@NavigationScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [NavigatorModule::class]
)
interface DefaultNavigationComponent {

    fun navigation(): Navigator

    @Component.Factory
    interface Factory {

        fun create(
            core: CoreComponent,
            @BindsInstance router: Router
        ): DefaultNavigationComponent
    }
}