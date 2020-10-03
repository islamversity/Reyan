package com.islamversity.base.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.islamversity.base.R

private const val DEFAULT_BACKGROUND_COLOR = Color.WHITE

class CurveView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    style: Int = 0
) : View(context, attrs, style) {

    private var bgColor = 0

    private val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    var radius = 0F
        set(value) {
            field = value
            invalidate()
        }

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CurveView)

        try {
            bgColor = ta.getColor(R.styleable.CurveView_cv_background, DEFAULT_BACKGROUND_COLOR)
            radius = ta.getDimensionPixelSize(R.styleable.CurveView_cv_radius, 0).toFloat()
        } finally {
            ta.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.apply {
            color = bgColor
            style = Paint.Style.FILL
        }

        canvas?.drawRoundRect(0F, 0F, width.toFloat(), height.toFloat(), radius, radius, paint)
    }
}
