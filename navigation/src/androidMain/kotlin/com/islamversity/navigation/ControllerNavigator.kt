package com.islamversity.navigation

import android.app.Application
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.islamversity.navigation.changehandler.ArcFadeMoveChangeHandlerCompat
import com.islamversity.navigation.changehandler.CircularRevealChangeHandlerCompat
import com.islamversity.navigation.changehandler.FlipChangeHandler
import com.islamversity.navigation.changehandler.ScaleFadeChangeHandler

class ControllerNavigator(
    private val app: Application,
    private val router: Router
) : Navigator {
    override fun goTo(screen: Screens) {
        val to = ControllerFactory.createController(screen, app)
        router.pushController(
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
                ArcFadeMoveChangeHandlerCompat(
                    *anim.transitionNames
                )
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