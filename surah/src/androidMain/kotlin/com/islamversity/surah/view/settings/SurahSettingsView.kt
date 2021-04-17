package com.islamversity.surah.view.settings

import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.islamversity.base.visible
import com.islamversity.domain.model.QuranReadFontSize
import com.islamversity.surah.R
import com.islamversity.surah.SurahIntent
import com.islamversity.surah.databinding.DialogSettingsBinding
import com.islamversity.surah.settings.SurahSettingsState
import com.islamversity.view_component.optionselector.DismissListener
import com.islamversity.view_component.optionselector.OptionSelector
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams

typealias OnSettings = (SurahIntent.ChangeSettings) -> Unit

class SurahSettingsView(
    context: Context,
    private val initialState: SurahSettingsState
) {

    private var binding: DialogSettingsBinding = DialogSettingsBinding.inflate(
        LayoutInflater
            .from(context)
    )

    val dialog = MaterialAlertDialogBuilder(context)
        .setView(binding.root)
        .setBackground(ContextCompat.getDrawable(context, R.drawable.dialog_bg))
        .create()
        .apply {
            bindViews()
        }


    var onSettings: OnSettings? = null

    private fun bindViews() {
        val context = binding.root.context

        render(initialState)

        binding.ayaFontSizeSeekBar.onSeekChangeListener = object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams) {
                onSettings?.invoke(SurahIntent.ChangeSettings.QuranFontSize(seekParams.progress))
            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {
                hideView()
                binding.dialogContainer.background = null
                dialog.window!!.setDimAmount(0F)
            }

            private fun hideView() {
                binding.apply {
                    ayaFontSizeSeekBar.updateLayoutParams<LinearLayout.LayoutParams> {
                        gravity = Gravity.CENTER
                    }
                    hideSiblings(ayaFontSizeSeekBar)
                }
            }

            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {
                dialog.dismiss()
            }
        }
        binding.transLateFontSizeSeekBar.onSeekChangeListener = object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams) {
                onSettings?.invoke(SurahIntent.ChangeSettings.TranslateFontSize(seekParams.progress))
            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {
                hideView()
                binding.dialogContainer.background = null
                dialog.window!!.setDimAmount(0F)
            }

            private fun hideView() {
                binding.apply {
                    transLateFontSizeSeekBar.updateLayoutParams<LinearLayout.LayoutParams> {
                        gravity = Gravity.CENTER
                    }
                    hideSiblings(transLateFontSizeSeekBar)
                }
            }

            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {
                dialog.dismiss()
            }
        }

        binding.ayaToolbarTitle.setOnCheckedChangeListener { buttonView, isChecked ->
            dismiss()
            onSettings?.invoke(SurahIntent.ChangeSettings.AyaToolbarVisibility(isChecked))
        }

        binding.firstTranslationCalligraphy.setOnClickListener {
            dismiss()
            OptionSelector(context)
                .options(initialState.ayaCalligraphies.map {
                    it.name.takeIf { it.isNotBlank() }
                        ?: binding.root.context.getString(R.string.setting_none_calligraphy_selected)
                })
                .dismissListener(object : DismissListener {
                    override fun dismissSheet(position: Int) {
                        onSettings?.invoke(SurahIntent.ChangeSettings.NewFirstTranslation(initialState.ayaCalligraphies[position]))
                    }
                }).show()
        }

        binding.secondTranslationCalligraphy.setOnClickListener {
            dismiss()
            OptionSelector(context)
                .options(initialState.ayaCalligraphies.map {
                    it.name.takeIf { it.isNotBlank() }
                        ?: binding.root.context.getString(R.string.setting_none_calligraphy_selected)
                })
                .dismissListener(object : DismissListener {
                    override fun dismissSheet(position: Int) {
                        onSettings?.invoke(SurahIntent.ChangeSettings.NewSecondTranslation(initialState.ayaCalligraphies[position]))
                    }
                }).show()
        }
    }

    private fun hideSiblings(view: View) {
        binding.dialogContainer.children.filter {
            it.id != view.id
        }.forEach {
            it visible false
        }
    }

    fun show() {
        dialog.show()
    }

    fun setOnCancelListener(listener: DialogInterface.OnCancelListener) {
        dialog.setOnCancelListener(listener)
    }

    fun dismiss() {
        dialog.dismiss()
    }


    private fun render(state: SurahSettingsState) {
        binding.ayaFontSizeSeekBar.min = QuranReadFontSize.MAIN_AYA_MIN.size.toFloat()
        binding.ayaFontSizeSeekBar.max = QuranReadFontSize.MAX.size.toFloat()

        binding.transLateFontSizeSeekBar.min = QuranReadFontSize.TRANSLATION_MIN.size.toFloat()
        binding.transLateFontSizeSeekBar.max = QuranReadFontSize.MAX.size.toFloat()

        binding.ayaFontSizeSeekBar.setProgress(state.quranTextFontSize.toFloat())
        binding.transLateFontSizeSeekBar.setProgress(state.translateTextFontSize.toFloat())

        binding.ayaToolbarTitle.isChecked = state.ayaToolbarVisible

        binding.firstTranslationCalligraphySubtitle.text =
            state.selectedFirstTranslationAyaCalligraphy?.name
                ?: binding.root.context.getString(R.string.aya_translation_not_chosen)

        binding.secondTranslationCalligraphySubtitle.text =
            state.selectedSecondTranslationAyaCalligraphy?.name
                ?: binding.root.context.getString(R.string.aya_translation_not_chosen)
    }
}