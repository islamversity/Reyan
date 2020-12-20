package com.islamversity.quran_home.feature.onboarding

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import com.airbnb.epoxy.*
import com.islamversity.base.widgets.ScalableTextView
import com.islamversity.quran_home.R
import com.islamversity.quran_home.databinding.ViewSelectionTextBinding

@ModelView(
    autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT,
    saveViewState = true
)
class TextSelectionView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr : Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private val binding = ViewSelectionTextBinding.inflate(LayoutInflater.from(context), this, true)
    init {
        gravity = Gravity.CENTER
    }

    @TextProp
    fun text(text: CharSequence) {
        binding.selectionTextView.text = text
    }

    @ModelProp(ModelProp.Option.DoNotHash)
    fun background(background: Drawable?) {
        if (background != null) {
            binding.selectionTextView.setTextColor(Color.WHITE)
        }else{
            binding.selectionTextView.setTextColor(Color.BLACK)
        }

        binding.selectionTextView.background = background
    }

    @CallbackProp
    fun clicks(action: ((text: String) -> Unit)?) {
        setOnClickListener{
            action?.invoke(binding.selectionTextView.text.toString())
        }

    }
}