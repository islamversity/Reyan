package com.islamversity.surah.view.settings

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.islamversity.domain.model.QuranReadFontSize
import com.islamversity.surah.SurahIntent
import com.islamversity.surah.databinding.DialogSettingsBinding
import com.islamversity.surah.settings.SurahSettingsState
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams

typealias OnSettings = (SurahIntent.ChangeSettings) -> Unit

class SurahSettingsView(
    context: Context,
    private val initialState: SurahSettingsState
) : Dialog(context) {
    private var binding: DialogSettingsBinding? = null
    var onSettings: OnSettings? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogSettingsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding!!.ayaFontSizeSeekBar.onSeekChangeListener = object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams) {
                onSettings?.invoke(SurahIntent.ChangeSettings.QuranFontSize(seekParams.progress))
            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {
                hideView()
                binding!!.dialogContainer.background = null
                window!!.setDimAmount(0F)
            }

            private fun hideView() {
                binding?.apply {
                    ayaFontSizeTitle.visibility = View.INVISIBLE
                    transLateFontSizeTitle.visibility = View.INVISIBLE
                    transLateFontSizeSeekBar.visibility = View.INVISIBLE
                }
            }

            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {
                dismiss()
            }
        }
        binding!!.transLateFontSizeSeekBar.onSeekChangeListener = object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams) {
                onSettings?.invoke(SurahIntent.ChangeSettings.TranslateFontSize(seekParams.progress))
            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {
                hideView()
                binding!!.dialogContainer.background = null
                window!!.setDimAmount(0F)
            }

            private fun hideView() {
                binding?.apply {
                    ayaFontSizeTitle.visibility = View.INVISIBLE
                    ayaFontSizeSeekBar.visibility = View.INVISIBLE
                    transLateFontSizeTitle.visibility = View.INVISIBLE
                }
            }

            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {
                dismiss()
            }
        }

        render(initialState)
    }

    private fun render(state: SurahSettingsState) {
        binding!!.ayaFontSizeSeekBar.min = QuranReadFontSize.MAIN_AYA_MIN.size.toFloat()
        binding!!.ayaFontSizeSeekBar.max = QuranReadFontSize.MAX.size.toFloat()

        binding!!.transLateFontSizeSeekBar.min = QuranReadFontSize.TRANSLATION_MIN.size.toFloat()
        binding!!.transLateFontSizeSeekBar.max = QuranReadFontSize.MAX.size.toFloat()

        binding!!.ayaFontSizeSeekBar.setProgress(state.quranTextFontSize.toFloat())
        binding!!.transLateFontSizeSeekBar.setProgress(state.translateTextFontSize.toFloat())
    }
}