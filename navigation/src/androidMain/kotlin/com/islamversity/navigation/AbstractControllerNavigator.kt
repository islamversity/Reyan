package com.islamversity.navigation

import android.app.Application
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.RouterTransaction

abstract class AbstractControllerNavigator(
    private val app: Application
) : Navigator {

    abstract fun routeTo(transaction: RouterTransaction)

    final override fun goTo(screen: Screens) {
        val to = ControllerFactory.createController(screen, app)
        routeTo(
         RouterTransaction.with(to)
//                .pushChangeHandler(getAnimation(screen.pushAnimation))
//                .popChangeHandler(getAnimation(screen.popAnimation))
        )
    }

    private fun getAnimation(anim: NavigationAnimation?): ControllerChangeHandler? {
        return null
    }
}