package com.islamversity.navigation

import android.app.Application
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction

class ControllerNavigator(
    app: Application,
    private val router: Router
) : AbstractControllerNavigator(app) {

    override fun routeTo(transaction: RouterTransaction) {
        router.pushController(transaction)
    }
}
