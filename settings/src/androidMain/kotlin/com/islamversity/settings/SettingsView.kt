package com.islamversity.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.islamversity.base.CoroutineView
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.daggercore.CoreComponent
import com.islamversity.domain.model.QuranReadFontSize
import com.islamversity.domain.model.TranslateReadFontSize
import com.islamversity.settings.databinding.ViewSettingsBinding
import com.islamversity.settings.di.DaggerSettingsComponent
import com.islamversity.settings.models.CalligraphyUIModel
import com.islamversity.settings.sheet.DismissListener
import com.islamversity.settings.sheet.OptionSelector
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import javax.inject.Inject


class SettingsView : CoroutineView<ViewSettingsBinding, SettingsState, SettingsIntent>() {

    private var defaultQuranSize = QuranReadFontSize.DEFAULT.size
    private var defaultTranslateSize = TranslateReadFontSize.DEFAULT.size
    private val fontRange = 20..50

    @Inject
    override lateinit var presenter: MviPresenter<SettingsIntent, SettingsState>

    private val intentChannel = BroadcastChannel<SettingsIntent>(Channel.BUFFERED)

    private var ayaCalligraphies: List<CalligraphyUIModel> = emptyList()
    private var surahNameCalligraphies: List<CalligraphyUIModel> = emptyList()

    override fun bindView(inflater: LayoutInflater, container: ViewGroup): ViewSettingsBinding =
        ViewSettingsBinding.inflate(inflater, container, false)

    override fun injectDependencies(core: CoreComponent) {
        DaggerSettingsComponent.factory().create(core).inject(this)
    }

    override fun beforeBindingView(binding: ViewSettingsBinding) {
        super.beforeBindingView(binding)
        binding.backButton.setOnClickListener { router.handleBack() }

        binding.surahCalligraphy.setOnClickListener {
            OptionSelector(binding.surahCalligraphy.context)
                .options(surahNameCalligraphies.map { it.name })
                .dismissListener(object : DismissListener {
                    override fun dismissSheet(position: Int) {
                        intentChannel.offer(
                            SettingsIntent.NewSurahNameCalligraphySelected(surahNameCalligraphies[position])
                        )
                    }
                })
                .show()
        }

        binding.ayaCalligraphy.setOnClickListener {
            OptionSelector(binding.surahCalligraphy.context)
                .options(ayaCalligraphies.map { it.name })
                .dismissListener(object : DismissListener {
                    override fun dismissSheet(position: Int) {
                        intentChannel.offer(
                            SettingsIntent.NewAyaCalligraphy(ayaCalligraphies[position])
                        )
                    }
                })
                .show()
        }
        binding.mainFontSizeSeekBar.min = fontRange.first.toFloat()
        binding.mainFontSizeSeekBar.max = fontRange.last.toFloat()
        binding.mainFontSizeSeekBar.onSeekChangeListener = object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams) {
                defaultQuranSize = seekParams.progress
                binding.mainFontSizeSubtitle.textSize = defaultQuranSize.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {
                intentChannel.offer(SettingsIntent.ChangeQuranFontSize(defaultQuranSize))
            }

        }

        binding.translateFontSizeSeekBar.min = fontRange.first.toFloat()
        binding.translateFontSizeSeekBar.max = fontRange.last.toFloat()
        binding.translateFontSizeSeekBar.onSeekChangeListener = object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams) {
                defaultTranslateSize = seekParams.progress
                binding.translateFontSizeSubtitle.textSize = defaultTranslateSize.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {
                intentChannel.offer(SettingsIntent.ChangeTranslateFontSize(defaultTranslateSize))
            }

        }

    }

    override fun render(state: SettingsState) {
        renderLoading(state.base)
        renderError(state.base)

        binding.surahCalligraphySubtitle.text = state.selectedSurahNameCalligraphy?.name
        binding.ayaCalligraphySubtitle.text =
            state.selectedAyaCalligraphy?.name ?: binding.root.context.getString(R.string.aya_translation_not_chosen)

        defaultQuranSize = state.quranTextFontSize
        binding.mainFontSizeSeekBar.setProgress(state.quranTextFontSize.toFloat())

        defaultTranslateSize = state.translateTextFontSize
        binding.translateFontSizeSeekBar.setProgress(state.translateTextFontSize.toFloat())

        ayaCalligraphies = state.ayaCalligraphies
        surahNameCalligraphies = state.surahNameCalligraphies
    }

    override fun intents(): Flow<SettingsIntent> =
        listOf(flowOf(SettingsIntent.Initial), intentChannel.asFlow())
            .merge()

}
