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
    app: Application,
    private val router: Router
) : AbstractControllerNavigator(app) {

    override fun routeTo(transaction: RouterTransaction) {
        router.pushController(transaction)
    }
}
