package com.islamversity.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.islamversity.base.CoroutineView
import com.islamversity.base.widgets.AppFontSizeStore
import com.islamversity.base.widgets.FontSizeScale
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.helpers.languageConfigure
import com.islamversity.domain.model.QuranReadFontSize
import com.islamversity.domain.model.TranslateReadFontSize
import com.islamversity.settings.databinding.ViewSettingsBinding
import com.islamversity.settings.di.DaggerSettingsComponent
import com.islamversity.settings.models.CalligraphyUIModel
import com.islamversity.view_component.optionselector.DismissListener
import com.islamversity.view_component.optionselector.OptionSelector
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

    @Inject
    override lateinit var presenter: MviPresenter<SettingsIntent, SettingsState>

    private val intentChannel = BroadcastChannel<SettingsIntent>(Channel.BUFFERED)

    private var firstTranslationCalligraphies: List<CalligraphyUIModel> = emptyList()
    private var secondTranslationCalligraphies: List<CalligraphyUIModel> = emptyList()
    private var surahNameCalligraphies: List<CalligraphyUIModel> = emptyList()

    lateinit var appLanguages: List<String>

    override fun bindView(inflater: LayoutInflater, container: ViewGroup): ViewSettingsBinding =
        ViewSettingsBinding.inflate(inflater, container, false)

    override fun injectDependencies(core: CoreComponent) {
        DaggerSettingsComponent.factory().create(core).inject(this)
    }

    override fun beforeBindingView(binding: ViewSettingsBinding) {
        super.beforeBindingView(binding)
        appLanguages = binding.root.context.resources.getStringArray(R.array.app_languages).toList()

        binding.backButton.setOnClickListener { router.handleBack() }

        binding.appLanguageContainer.setOnClickListener {
            OptionSelector(binding.appLanguageContainer.context)
                .options(languageConfigure.getSupportedLanguages().map { it.localeName })
                .dismissListener(object : DismissListener {
                    override fun dismissSheet(position: Int) {
                        languageConfigure.setNewLanguage(languageConfigure.getSupportedLanguages()[position].locale)
                    }
                })
                .show()
        }
        binding.tvAppLanguageSubTitle.text = languageConfigure.getCurrentLocale().localeName

        binding.appFontSizeContainer.setOnClickListener { clickedView ->
            OptionSelector(binding.secondSurahCalligraphy.context)
                .options(FontSizeScale.values().map { clickedView.context.getString(it.label) })
                .dismissListener(object : DismissListener {
                    override fun dismissSheet(position: Int) {
                        AppFontSizeStore.setScale(FontSizeScale.values()[position])
                    }
                })
                .show()
        }
        binding.tvAppFontSizeSubTitle.setText(AppFontSizeStore.getCurrentScale().label)

        binding.secondSurahCalligraphy.setOnClickListener {
            OptionSelector(binding.secondSurahCalligraphy.context)
                .options(surahNameCalligraphies.map { it.name })
                .dismissListener(object : DismissListener {
                    override fun dismissSheet(position: Int) {
                        intentChannel.offer(
                            SettingsIntent.NewSecondSurahNameCalligraphySelected(surahNameCalligraphies[position])
                        )
                    }
                })
                .show()
        }

        binding.firstTranslationCalligraphy.setOnClickListener {
            OptionSelector(binding.secondSurahCalligraphy.context)
                .options(firstTranslationCalligraphies.map { it.name.takeIf { it.isNotBlank() } ?: binding.root.context.getString(R.string.setting_none_calligraphy_selected)})
                .dismissListener(object : DismissListener {
                    override fun dismissSheet(position: Int) {
                        intentChannel.offer( SettingsIntent.NewFirstTranslation(firstTranslationCalligraphies[position]))
                    }
                }).show()
        }

        binding.secondTranslationCalligraphy.setOnClickListener {
            OptionSelector(binding.secondSurahCalligraphy.context)
                .options(secondTranslationCalligraphies.map { it.name.takeIf { it.isNotBlank() } ?: binding.root.context.getString(R.string.setting_none_calligraphy_selected) })
                .dismissListener(object : DismissListener {
                    override fun dismissSheet(position: Int) {
                        intentChannel.offer(SettingsIntent.NewSecondTranslation(secondTranslationCalligraphies[position]))
                    }
                }).show()

        }
        binding.mainFontSizeSeekBar.min = QuranReadFontSize.MAIN_AYA_MIN.size.toFloat()
        binding.mainFontSizeSeekBar.max = QuranReadFontSize.MAX.size.toFloat()
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

        binding.translateFontSizeSeekBar.min = QuranReadFontSize.TRANSLATION_MIN.size.toFloat()
        binding.translateFontSizeSeekBar.max = QuranReadFontSize.MAX.size.toFloat()
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

        binding.secondSurahCalligraphySubtitle.text = state.selectedSecondSurahNameCalligraphy?.name
        binding.firstTranslationCalligraphySubtitle.text =
            state.selectedFirstTranslationCalligraphy?.name
                ?: binding.root.context.getString(R.string.aya_translation_not_chosen)

        defaultQuranSize = state.quranTextFontSize
        binding.mainFontSizeSeekBar.setProgress(state.quranTextFontSize.toFloat())

        defaultTranslateSize = state.translateTextFontSize
        binding.translateFontSizeSeekBar.setProgress(state.translateTextFontSize.toFloat())

        firstTranslationCalligraphies = state.firstTranslationCalligraphies
        surahNameCalligraphies = state.secondSurahNameCalligraphies
        secondTranslationCalligraphies = state.secondTranslationCalligraphies
        binding.secondTranslationCalligraphySubtitle.text =
            state.selectedSecondTranslationCalligraphy?.name
        binding.secondTranslationCalligraphySubtitle.text =
            state.selectedSecondTranslationCalligraphy?.name
                ?: binding.root.context.getString(R.string.aya_translation_not_chosen)
    }

    override fun intents(): Flow<SettingsIntent> =
        listOf(flowOf(SettingsIntent.Initial), intentChannel.asFlow())
            .merge()

}
