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
import com.islamversity.surah.R
import com.islamversity.surah.databinding.RowAyaBinding
import com.islamversity.surah.model.AyaUIModel
import com.islamversity.surah.model.SajdahTypeUIModel

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
        binding.tvAyaTranslate1.text = surah.translation1
        binding.tvAyaTranslate2.text = surah.translation2
        binding.hizbOrder.text = (surah.hizb ?: 0).toString()
        binding.juzOrder.text = (surah.juz ?: 0).toString()

        binding.tvAyaContent.textSize = surah.fontSize.toFloat()
        binding.tvAyaTranslate1.textSize = surah.translationFontSize.toFloat()
        binding.tvAyaTranslate2.textSize = surah.translationFontSize.toFloat()

        binding.tvAyaTranslate1 visible (surah.translation1 != null)
        binding.tvAyaTranslate2 visible (surah.translation2 != null)

        binding.hizbOrder visible (surah.hizb != null)
        binding.juzOrder visible (surah.juz != null)

        binding.ayaToolbar visible surah.toolbarVisible

        binding.sajdahIcon visible (surah.sajdah is SajdahTypeUIModel.VISIBLE)
        binding.sajdahTitle visible (surah.sajdah is SajdahTypeUIModel.VISIBLE)
        binding.sajdahTitle.text = when (surah.sajdah) {
            SajdahTypeUIModel.VISIBLE.RECOMMENDED -> context.resources.getString(R.string.sajdah_type_recommended)
            SajdahTypeUIModel.VISIBLE.OBLIGATORY -> context.resources.getString(R.string.sajdah_type_obligatory)
            SajdahTypeUIModel.NONE -> null
        }
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

