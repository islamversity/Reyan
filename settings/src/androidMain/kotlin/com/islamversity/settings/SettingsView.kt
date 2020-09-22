package com.islamversity.settings

import android.util.Log
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

    private var defaultSize = "Default"

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

        val sizeList = binding.fontSize.context.resources.getStringArray(R.array.font_size).toList()
        binding.fontSize.setOnClickListener {
            OptionSelector(binding.surahCalligraphy.context)
                .options(sizeList)
                .defaultPosition(sizeList.indexOf(defaultSize))
                .orientation(RecyclerView.VERTICAL)
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

        val sizeList = binding.fontSize.context.resources.getStringArray(R.array.font_size).toList()
        Log.i("TAG", "render state.quranTextFontSize: " + state.quranTextFontSize)
        defaultSize = sizeList[if (state.quranTextFontSize > sizeList.size) 1 else state.quranTextFontSize]
        binding.fontSizeSubtitle.text = defaultSize


        ayaCalligraphies = state.ayaCalligraphies
        surahNameCalligraphies = state.surahNameCalligraphies
    }

    override fun intents(): Flow<SettingsIntent> =
        listOf(flowOf(SettingsIntent.Initial), intentChannel.asFlow())
            .merge()

}
