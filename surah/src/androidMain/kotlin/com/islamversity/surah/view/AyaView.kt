package com.islamversity.surah.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.islamversity.base.visible
import com.islamversity.surah.databinding.RowAyaBinding
import com.islamversity.surah.model.AyaUIModel

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    saveViewState = true
)
class AyaView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    private val binding = RowAyaBinding.inflate(LayoutInflater.from(context), this, true)

    @SuppressLint("SetTextI18n")
    @ModelProp
    fun model(surah: AyaUIModel) {

        binding.ayaOrder.txtOrder.text = surah.order.toString()
        binding.tvAyaContent.text = surah.content
        binding.tvAyaTranslate1.text = surah.content
        binding.tvAyaTranslate2.text = surah.content

        binding.tvAyaContent.textSize = surah.fontSize.toFloat()
        binding.tvAyaTranslate1.textSize = surah.fontSize.toFloat()
        binding.tvAyaTranslate2.textSize = surah.fontSize.toFloat()

        binding.ayaToolbar visible surah.toolbarVisible
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

