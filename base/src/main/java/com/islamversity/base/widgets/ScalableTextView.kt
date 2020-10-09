package com.islamversity.base.widgets

import android.content.Context
import android.content.SharedPreferences
import android.util.AttributeSet
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_PX
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import com.islamversity.base.R

enum class FontSizeScale(
    internal val raw: String,
    internal val scale: Float,
    @StringRes val label: Int,
) {
    SMALL("SMALL", 0.5F, R.string.title_font_size_small),
    MEDIUM("MEDIUM", 0.75F, R.string.title_font_size_medium),
    NORMAL("NORMAL", 1F, R.string.title_font_size_normal),
    LARGE("LARGE", 1.5F, R.string.title_font_size_Large),
    X_LARGE("X_LARGE", 2F, R.string.title_font_size_x_Large),
    ;

    companion object {
        operator fun invoke(raw: String): FontSizeScale =
            values().find { it.raw == raw }!!
    }
}

object AppFontSizeStore {
    private const val NAME_APP_FONT_SIZE_PREFS = "NAME_APP_FONT_SIZE_PREFS"
    internal const val KEY_APP_FONT_SIZE_SCALE = "KEY_APP_FONT_SIZE_SCALE"

    internal lateinit var sharedPrefs: SharedPreferences

    fun init(context: Context) {
        if (!::sharedPrefs.isInitialized) {
            sharedPrefs = context.applicationContext.getSharedPreferences(NAME_APP_FONT_SIZE_PREFS, Context.MODE_PRIVATE)
        }
    }

    fun getCurrentScale() = FontSizeScale(sharedPrefs.getString(KEY_APP_FONT_SIZE_SCALE, FontSizeScale.NORMAL.raw)!!)

    fun setScale(scale: FontSizeScale) {
        sharedPrefs.edit().putString(KEY_APP_FONT_SIZE_SCALE, scale.raw).apply()
    }
}

class ScalableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    style: Int = 0
) : AppCompatTextView(context, attrs, style) {

    private val attrFontSize : Float = textSize

    private var fontSizeScale: FontSizeScale = AppFontSizeStore.getCurrentScale()

    private val fontSizeChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == AppFontSizeStore.KEY_APP_FONT_SIZE_SCALE) {
            fontSizeScale = AppFontSizeStore.getCurrentScale()
            setTextSize(COMPLEX_UNIT_PX, attrFontSize)
        }
    }

    init {
        //to set the default stored value
        setTextSize(COMPLEX_UNIT_PX, attrFontSize)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        AppFontSizeStore.sharedPrefs.registerOnSharedPreferenceChangeListener(fontSizeChangeListener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        AppFontSizeStore.sharedPrefs.unregisterOnSharedPreferenceChangeListener(fontSizeChangeListener)
    }

    override fun setTextSize(unit: Int, size: Float) {
        super.setTextSize(unit, size * fontSizeScale.scale)
    }
}
