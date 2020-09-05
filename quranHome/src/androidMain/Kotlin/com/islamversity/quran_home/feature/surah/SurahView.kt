package com.islamversity.quran_home.feature.surah

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.islamversity.quran_home.databinding.SurahViewBinding
import com.islamversity.quran_home.feature.surah.model.SurahUIModel

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    saveViewState = true
)
class SurahView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    private val binding = SurahViewBinding.inflate(LayoutInflater.from(context), this, true)

    @SuppressLint("SetTextI18n")
    @ModelProp
    fun surah(surah: SurahUIModel){
        binding.title.text = surah.name
        binding.index.text = surah.order.toString()
        binding.reveal.text = "(${surah.revealedType.name})"
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

