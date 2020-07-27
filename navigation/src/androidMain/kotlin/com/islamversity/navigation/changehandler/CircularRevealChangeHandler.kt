package com.islamversity.navigation.changehandler

import android.animation.Animator
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.annotation.Keep
import com.bluelinelabs.conductor.changehandler.AnimatorChangeHandler

/**
 * An [AnimatorChangeHandler] that will perform a circular reveal
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
open class CircularRevealChangeHandler : AnimatorChangeHandler {
    private var cx = 0
    private var cy = 0

    @Keep
    constructor() {
    }

    /**
     * Constructor that will create a circular reveal from the center of the fromView parameter.
     * @param fromView The view from which the circular reveal should originate
     * @param containerView The view that hosts fromView
     * @param removesFromViewOnPush If true, the view being replaced will be removed from the view hierarchy on pushes
     */
    constructor(
        fromView: View,
        containerView: View,
        removesFromViewOnPush: Boolean
    ) : this(fromView, containerView, DEFAULT_ANIMATION_DURATION, true) {
    }
    /**
     * Constructor that will create a circular reveal from the center of the fromView parameter.
     * @param fromView The view from which the circular reveal should originate
     * @param containerView The view that hosts fromView
     * @param duration The duration of the animation
     * @param removesFromViewOnPush If true, the view being replaced will be removed from the view hierarchy on pushes
     */
    /**
     * Constructor that will create a circular reveal from the center of the fromView parameter.
     * @param fromView The view from which the circular reveal should originate
     * @param containerView The view that hosts fromView
     * @param duration The duration of the animation
     */
    /**
     * Constructor that will create a circular reveal from the center of the fromView parameter.
     * @param fromView The view from which the circular reveal should originate
     * @param containerView The view that hosts fromView
     */
    @JvmOverloads
    constructor(
        fromView: View,
        containerView: View,
        duration: Long = DEFAULT_ANIMATION_DURATION,
        removesFromViewOnPush: Boolean = true
    ) : super(duration, removesFromViewOnPush) {
        val fromLocation = IntArray(2)
        fromView.getLocationInWindow(fromLocation)
        val containerLocation = IntArray(2)
        containerView.getLocationInWindow(containerLocation)
        val relativeLeft = fromLocation[0] - containerLocation[0]
        val relativeTop = fromLocation[1] - containerLocation[1]
        cx = fromView.width / 2 + relativeLeft
        cy = fromView.height / 2 + relativeTop
    }

    /**
     * Constructor that will create a circular reveal from the center point passed in.
     * @param cx The center's x-axis
     * @param cy The center's y-axis
     * @param removesFromViewOnPush If true, the view being replaced will be removed from the view hierarchy on pushes
     */
    constructor(cx: Int, cy: Int, removesFromViewOnPush: Boolean) : this(
        cx,
        cy,
        DEFAULT_ANIMATION_DURATION,
        removesFromViewOnPush
    ) {
    }
    /**
     * Constructor that will create a circular reveal from the center point passed in.
     * @param cx The center's x-axis
     * @param cy The center's y-axis
     * @param duration The duration of the animation
     * @param removesFromViewOnPush If true, the view being replaced will be removed from the view hierarchy on pushes
     */
    /**
     * Constructor that will create a circular reveal from the center point passed in.
     * @param cx The center's x-axis
     * @param cy The center's y-axis
     */
    /**
     * Constructor that will create a circular reveal from the center point passed in.
     * @param cx The center's x-axis
     * @param cy The center's y-axis
     * @param duration The duration of the animation
     */
    @JvmOverloads
    constructor(
        cx: Int,
        cy: Int,
        duration: Long = DEFAULT_ANIMATION_DURATION,
        removesFromViewOnPush: Boolean = true
    ) : super(duration, removesFromViewOnPush) {
        this.cx = cx
        this.cy = cy
    }

    override fun getAnimator(
        container: ViewGroup,
        from: View?,
        to: View?,
        isPush: Boolean,
        toAddedToContainer: Boolean
    ): Animator {
        val radius =
            Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
        var animator: Animator? = null
        if (isPush && to != null) {
            animator = ViewAnimationUtils.createCircularReveal(to, cx, cy, 0f, radius)
        } else if (!isPush && from != null) {
            animator =
                ViewAnimationUtils.createCircularReveal(from, cx, cy, radius, 0f)
        }
        return animator!!
    }

    override fun resetFromView(from: View) {}
    override fun saveToBundle(bundle: Bundle) {
        super.saveToBundle(bundle)
        bundle.putInt(KEY_CX, cx)
        bundle.putInt(KEY_CY, cy)
    }

    override fun restoreFromBundle(bundle: Bundle) {
        super.restoreFromBundle(bundle)
        cx = bundle.getInt(KEY_CX)
        cy = bundle.getInt(KEY_CY)
    }

    companion object {
        private const val KEY_CX = "CircularRevealChangeHandler.cx"
        private const val KEY_CY = "CircularRevealChangeHandler.cy"
    }
}