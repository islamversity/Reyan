package com.islamversity.navigation

import android.app.Application
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.RouterTransaction
import com.islamversity.navigation.changehandler.ArcFadeMoveChangeHandlerCompat
import com.islamversity.navigation.changehandler.CircularRevealChangeHandlerCompat
import com.islamversity.navigation.changehandler.FlipChangeHandler
import com.islamversity.navigation.changehandler.ScaleFadeChangeHandler

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
        if (anim == null) {
            return null
        }

        return when (anim) {
            is NavigationAnimation.ArcFadeMove ->
                ArcFadeMoveChangeHandlerCompat()
            is NavigationAnimation.CircularReveal ->
                if (anim.duration == -1L) {
                    CircularRevealChangeHandlerCompat(
                        anim.fromCX,
                        anim.fromCY
                    )
                } else {
                    CircularRevealChangeHandlerCompat(
                        anim.fromCX,
                        anim.fromCY,
                        anim.duration
                    )
                }
            NavigationAnimation.Flip ->
                FlipChangeHandler()
            NavigationAnimation.ScaleFade ->
                ScaleFadeChangeHandler()
        }
    }
}