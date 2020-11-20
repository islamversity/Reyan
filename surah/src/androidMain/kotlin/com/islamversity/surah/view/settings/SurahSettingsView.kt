package com.islamversity.surah.view.settings

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.islamversity.view_component.sheet.DismissListener
import com.islamversity.view_component.sheet.OptionSelector
import com.islamversity.domain.model.QuranReadFontSize
import com.islamversity.surah.SurahIntent
import com.islamversity.surah.databinding.DialogSettingsBinding
import com.islamversity.surah.model.CalligraphyUIModel
import com.islamversity.surah.settings.SurahSettingsState
import com.islamversity.view_component.ext.hide
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams

typealias OnSettings = (SurahIntent.ChangeSettings) -> Unit

class SurahSettingsView(
    context: Context,
    private val initialState: SurahSettingsState
) : Dialog(context) {
    private lateinit var ayaCalligraphies: List<CalligraphyUIModel>
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
                    ayaFontSizeTitle.hide()
                    transLateFontSizeTitle.hide()
                    transLateFontSizeSeekBar..hide()
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
                    ayaFontSizeTitle.hide()
                    changeFirstTranslate.hide()
                    changeSecondTranslate.hide()
                    showAyaToolbarSwitch.hide()
                    ayaFontSizeSeekBar.hide()
                    transLateFontSizeTitle.hide()
                }
            }

            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {
                dismiss()
            }
        }

        binding!!.showAyaToolbarSwitch.setOnCheckedChangeListener { view, isChecked ->
            onSettings?.invoke(SurahIntent.ChangeSettings.ShowAyaTollbar(isChecked))
        }

        binding!!.changeFirstTranslate.setOnClickListener {
            dismiss()
            OptionSelector(it.context)
                .options(ayaCalligraphies.map { it.name })
                .dismissListener(object : DismissListener {
                    override fun dismissSheet(position: Int) {
                        onSettings!!.invoke(SurahIntent
                            .ChangeSettings
                            .NewFirstTranslation(ayaCalligraphies[position]))
                        dismiss()
                    }
                })
                .show()
        }

        binding!!.changeSecondTranslate.setOnClickListener {
            dismiss()
            OptionSelector(it.context)
                .options(ayaCalligraphies.map { it.name })
                .dismissListener(object : DismissListener {
                    override fun dismissSheet(position: Int) {
                        onSettings!!.invoke(SurahIntent
                            .ChangeSettings
                            .NewSecondTranslation(ayaCalligraphies[position]))
                        dismiss()
                    }
                }).show()

        }

        render(initialState)
    }

    private fun render(state: SurahSettingsState) {
        binding!!.apply {
            ayaFontSizeSeekBar.min = QuranReadFontSize.MAIN_AYA_MIN.size.toFloat()
            ayaFontSizeSeekBar.max = QuranReadFontSize.MAX.size.toFloat()

            transLateFontSizeSeekBar.min = QuranReadFontSize.TRANSLATION_MIN.size.toFloat()
            transLateFontSizeSeekBar.max = QuranReadFontSize.MAX.size.toFloat()

            ayaFontSizeSeekBar.setProgress(state.quranTextFontSize.toFloat())
            transLateFontSizeSeekBar.setProgress(state.translateTextFontSize.toFloat())

            showAyaToolbarSwitch.isChecked = state.toolbarForAyaOption

            ayaCalligraphies = state.ayaCalligraphies
        }

    }
}