package com.islamversity.surah.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.islamversity.base.getColorCompat
import com.islamversity.base.visible
import com.islamversity.surah.databinding.SurahHeaderBinding
import com.islamversity.surah.model.SurahHeaderUIModel

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class SurahHeader(
    context: Context,
) : LinearLayout(context) {

    private val binding = SurahHeaderBinding.inflate(LayoutInflater.from(context), this, true)

    @ModelProp
    fun model(surahHeaderUIModel: SurahHeaderUIModel) {

        binding.apply {
            tvSurahNumber.txtOrder.text = surahHeaderUIModel.number
            tvSurahNumber.ivSurahOrderShape.setColorFilter(root.context getColorCompat android.R.color.white)
            tvSurahNumber.txtOrder.setTextColor(root.context getColorCompat android.R.color.white)

            tvSurahName.text = surahHeaderUIModel.name
            tvSurahNameTranslate.text = surahHeaderUIModel.nameTranslated

            tvSurahOrigin.text = surahHeaderUIModel.origin
            tvSurahVerses.text = surahHeaderUIModel.verses.toString()

            ivBismillah visible surahHeaderUIModel.showBismillah

            tvSurahName.textSize = surahHeaderUIModel.fontSize.toFloat()
            tvSurahNameTranslate.textSize = surahHeaderUIModel.fontSize.toFloat()
            tvSurahOrigin.textSize = surahHeaderUIModel.fontSize.toFloat()
            tvSurahVerses.textSize = surahHeaderUIModel.fontSize.toFloat()
        }
    }
}
