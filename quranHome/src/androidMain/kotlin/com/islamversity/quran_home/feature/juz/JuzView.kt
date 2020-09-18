package com.islamversity.quran_home.feature.juz

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.islamversity.quran_home.R
import com.islamversity.quran_home.databinding.RowJuzBinding
import com.islamversity.quran_home.feature.juz.model.JozUIModel

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    saveViewState = true
)
class JuzView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    private val binding = RowJuzBinding.inflate(LayoutInflater.from(context), this, true)
    private val juzTitle = context.resources.getStringArray(R.array.juz_title)

    @ModelProp
    fun juz(juz: JozUIModel) {
        binding.juzNumber.text = juz.number.toString()
        binding.juzTitle.text = juzTitle[juz.number.toInt()]
        binding.startJuzTitle.text = juz.startTitle
        binding.endJuzTitle.text = juz.endTitle
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

