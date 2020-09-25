package com.islamversity.base.widgets

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.constraintlayout.motion.widget.MotionLayout
import kotlinx.android.parcel.Parcelize

class SaveStateMotionLayout @JvmOverloads constructor(
    context: Context,
    attrs : AttributeSet? = null,
    defStyleAttr : Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {
    @Parcelize
    data class SavedState(
        val progress : Float, val superState : Parcelable?
    ) : Parcelable


    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(progress, super.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            progress = state.progress
            super.onRestoreInstanceState(state.superState)
        } else super.onRestoreInstanceState(state)
    }
}