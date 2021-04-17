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
import com.islamversity.surah.SurahIntent
import com.islamversity.surah.databinding.RowAyaBinding
import com.islamversity.surah.model.AyaUIModel
import com.islamversity.surah.model.SajdahTypeUIModel
import java.text.NumberFormat
import java.util.*

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    saveViewState = true
)
class AyaView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    private val numberFormatter = NumberFormat.getInstance(Locale.getDefault())
    private val binding = RowAyaBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var model: AyaUIModel

    @SuppressLint("SetTextI18n")
    @ModelProp
    fun model(surah: AyaUIModel) {
        model = surah
        binding.tvAyaContent.text = surah.content
        binding.tvAyaTranslate1.text = surah.translation1
        binding.tvAyaTranslate2.text = surah.translation2

        binding.tvAyaTranslate1 visible (surah.translation1 != null)
        binding.tvAyaTranslate2 visible (surah.translation2 != null)

        binding.ayaOrder.txtOrder.text = numberFormatter.format(surah.order)

        bindHizbAndJuz(surah.juz, surah.hizb)

        binding.ayaToolbar visible surah.toolbarVisible

        binding.sajdahIcon visible (surah.sajdah is SajdahTypeUIModel.VISIBLE)
        binding.sajdahTitle visible (surah.sajdah is SajdahTypeUIModel.VISIBLE)
        binding.sajdahTitle.text = when (surah.sajdah) {
            SajdahTypeUIModel.VISIBLE.RECOMMENDED -> context.resources.getString(R.string.sajdah_type_recommended)
            SajdahTypeUIModel.VISIBLE.OBLIGATORY -> context.resources.getString(R.string.sajdah_type_obligatory)
            SajdahTypeUIModel.NONE -> null
        }
    }

    private fun bindHizbAndJuz(juz: Long?, hizbProgress: AyaUIModel.HizbProgress?) {
        if (juz != null) {
            bindJuz(juz, hizbProgress?.hizb ?: error("beginning of juz hizb can not be null"))
            binding.layoutJuzHizb.root visible true
            binding.layoutHizbPartial.root visible false
            return
        } else {
            binding.layoutJuzHizb.root visible false
        }

        if (hizbProgress != null) {
            bindHizb(hizbProgress)
            binding.layoutHizbPartial.root visible true
        } else {
            binding.layoutHizbPartial.root visible false
        }
    }

    private fun bindJuz(juz: Long, hizb: Long) {
        binding.layoutJuzHizb.juzOrder.text = numberFormatter.format(juz)
        binding.layoutJuzHizb.hizbOrder.text = numberFormatter.format(hizb)
    }

    private fun bindHizb(hizbProgress: AyaUIModel.HizbProgress) {
        binding.layoutHizbPartial.hizbOrder.text = numberFormatter.format(hizbProgress.hizb)

        if (hizbProgress is AyaUIModel.HizbProgress.Beginning) {
            binding.layoutHizbPartial.hizbPartial visible false
        } else {
            binding.layoutHizbPartial.hizbPartial visible true
            binding.layoutHizbPartial.hizbPartial.text = hizbProgress.toLocalString()
        }
    }

    private fun AyaUIModel.HizbProgress.toLocalString() =
        when (this) {
            is AyaUIModel.HizbProgress.Beginning -> error("beginning is not supported for fraction strings, other type of view has to be shown, $model")
            is AyaUIModel.HizbProgress.Half -> context.getString(R.string.hizb_half)
            is AyaUIModel.HizbProgress.Quarter -> context.getString(R.string.hizb_quarter)
            is AyaUIModel.HizbProgress.ThreeFourth -> context.getString(R.string.hizb_three_fourth)
        }

    @ModelProp
    fun mainAyaFontSize(size: Int) {
        binding.tvAyaContent.textSize = size.toFloat()
    }

    @ModelProp
    fun translationFontSize(size: Int) {
        binding.tvAyaTranslate1.textSize = size.toFloat()
        binding.tvAyaTranslate2.textSize = size.toFloat()
    }

    @ModelProp
    fun toolbarVisible(visible: Boolean) {
        binding.ayaToolbar visible visible
    }

    @CallbackProp
    fun listener(call: ((SurahIntent.AyaActions) -> Unit)?) {
        if (call == null) {
            binding.ivAyaShare.setOnClickListener(null)
        } else {
            binding.ivAyaShare.setOnClickListener {
                call(SurahIntent.AyaActions.Share(model))
            }
        }
    }
}

