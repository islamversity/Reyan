package com.islamversity.navigation.changehandler

import android.annotation.TargetApi
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.transition.Transition
import com.bluelinelabs.conductor.changehandler.androidxtransition.SharedElementTransitionChangeHandler
import java.util.*

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class ArcFadeMoveChangeHandler : SharedElementTransitionChangeHandler {
    private val sharedElementNames = ArrayList<String>()

    @Keep
    constructor() {
    }

    constructor(vararg sharedElements: String) {
        Collections.addAll(this.sharedElementNames, *sharedElements)
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