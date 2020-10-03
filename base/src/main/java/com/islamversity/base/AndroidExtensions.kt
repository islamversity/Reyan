package com.islamversity.base

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.bluelinelabs.conductor.Controller

fun Context.getScreenWidth(): Int =
    resources.displayMetrics.widthPixels

fun Context.getScreenHeight(): Int =
    resources.displayMetrics.widthPixels

@ColorInt
infix fun Context.getColorCompat(@ColorRes id: Int): Int =
    ContextCompat.getColor(this, id)

infix fun Float.pixel(context: Context): Float =
    this *
            (context.resources.displayMetrics.densityDpi.toFloat() /
                    DisplayMetrics.DENSITY_DEFAULT)

infix fun Float.dp(context: Context): Float =
    this /
            (context.resources.displayMetrics.densityDpi.toFloat() /
                    DisplayMetrics.DENSITY_DEFAULT)

fun View.hideKeyboard() {
    val imm = this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.showKeyboard() {
    val imm = this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Controller.showKeyboard() {
    view?.run {
        showKeyboard()
    }
}

fun Controller.hideKeyboard() {
    view?.run {
        hideKeyboard()
    }
}
