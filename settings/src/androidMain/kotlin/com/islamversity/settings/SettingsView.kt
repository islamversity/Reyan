package com.islamversity.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.islamversity.base.CoroutineView
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.daggercore.CoreComponent
import com.islamversity.settings.databinding.ViewSettingsBinding
import com.islamversity.settings.di.DaggerSettingsComponent
import com.islamversity.settings.models.CalligraphyUIModel
import com.islamversity.settings.sheet.DismissListener
import com.islamversity.settings.sheet.OptionSelector
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class SettingsView : CoroutineView<ViewSettingsBinding, SettingsState, SettingsIntent>() {

    private var defaultSize = 0

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
        binding.backButton.setOnClickListener { handleBack() }

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

        binding.fontSize.setOnClickListener {
            OptionSelector(binding.surahCalligraphy.context)
                .options((1..50).map { it.toString() })
                .defaultPosition(defaultSize)
                .maxHeight(300)
                .orientation(RecyclerView.HORIZONTAL)
                .dismissListener(object : DismissListener {
                    override fun dismissSheet(position: Int) {
                        intentChannel.offer(SettingsIntent.ChangeQuranFontSize(position))
                    }
                })
                .show()
        }

    }

    override fun render(state: SettingsState) {
        renderLoading(state.base)
        renderError(state.base)

        binding.surahCalligraphySubtitle.text = state.selectedSurahNameCalligraphy?.name
        binding.ayaCalligraphySubtitle.text = state.selectedAyaCalligraphy?.name


        defaultSize = state.quranTextFontSize
        binding.fontSizeSubtitle.text = state.quranTextFontSize.toString()


        ayaCalligraphies = state.ayaCalligraphies
        surahNameCalligraphies = state.surahNameCalligraphies
    }

    override fun intents(): Flow<SettingsIntent> =
        listOf(flowOf(SettingsIntent.Initial), intentChannel.asFlow())
            .merge()

}
