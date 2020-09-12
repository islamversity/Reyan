package com.islamversity.settings.sheet

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.islamversity.settings.databinding.RowOptionBinding

@ModelView(
    autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT,
    saveViewState = true
)
class OptionView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    private val binding = RowOptionBinding.inflate(LayoutInflater.from(context), this, true)

    @ModelProp
    fun item(item: String) {
        binding.title.text = item
    }

    @CallbackProp
    fun listener(listener: (() -> Unit)?) {
        if (listener == null) {
            binding.root.setOnClickListener(null)
        } else {
            binding.root.setOnClickListener {
                listener()
            }
        }
    }
}