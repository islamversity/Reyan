package com.islamversity.navigation.changehandler

import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.transition.Transition
import com.bluelinelabs.conductor.changehandler.androidxtransition.SharedElementTransitionChangeHandler

class ArcFadeMoveChangeHandlerCompat : SharedElementTransitionChangeHandler {
    @Keep
    constructor() : super() {
    }

    override fun configureSharedElements(container: ViewGroup, from: View?, to: View?, isPush: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getExitTransition(container: ViewGroup, from: View?, to: View?, isPush: Boolean): Transition? {
        TODO("Not yet implemented")
    }

    override fun getSharedElementTransition(
        container: ViewGroup,
        from: View?,
        to: View?,
        isPush: Boolean
    ): Transition? {
        TODO("Not yet implemented")
    }

    override fun getEnterTransition(container: ViewGroup, from: View?, to: View?, isPush: Boolean): Transition? {
        TODO("Not yet implemented")
    }
}