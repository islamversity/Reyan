package com.islamversity.quran_home.feature.surah

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.islamversity.quran_home.R
import com.islamversity.quran_home.feature.surah.model.SurahUIModel
import com.islamversity.view_component.databinding.RowSurahBinding

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    saveViewState = true
)
class SurahView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    private val binding = RowSurahBinding.inflate(LayoutInflater.from(context), this, true)
    private val ayaTitle by lazy { context.getString(R.string.aya) }

    @SuppressLint("SetTextI18n")
    @ModelProp
    fun surah(surah: SurahUIModel) {
        binding.name.text = surah.name
        binding.order.text = surah.order.toString()
        binding.revealedAyaCount.text = "${surah.revealedType.name} - ${surah.ayaCount} $ayaTitle"
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

