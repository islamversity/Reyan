package com.islamversity.settings

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.SeekBar
import com.islamversity.base.CoroutineView
import com.islamversity.base.epoxyhelper.EpoxyAsyncRecyclerView
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.daggercore.CoreComponent
import com.islamversity.settings.databinding.ViewSettingsBinding
import com.islamversity.settings.di.DaggerSettingsComponent
import com.islamversity.settings.models.CalligraphyUIModel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class SettingsView(
) : CoroutineView<ViewSettingsBinding, SettingsState, SettingsIntent>() {

    @Inject
    override lateinit var presenter: MviPresenter<SettingsIntent, SettingsState>

    private val intentChannel = BroadcastChannel<SettingsIntent>(Channel.BUFFERED)

    private var ayaCalligraphies  : List<CalligraphyUIModel> = emptyList()
    private var surahNameCalligraphies  : List<CalligraphyUIModel> = emptyList()

    private var surahNameCalligraphiesView : PopupWindow? = null
    private var ayaCalligraphiesView : PopupWindow? = null

    override fun bindView(inflater: LayoutInflater, container: ViewGroup): ViewSettingsBinding =
        ViewSettingsBinding.inflate(inflater, container, false)

    override fun injectDependencies(core: CoreComponent) {
        DaggerSettingsComponent.factory().create(core).inject(this)
    }

    override fun beforeBindingView(binding: ViewSettingsBinding) {
        super.beforeBindingView(binding)
        binding.skFontSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                intentChannel.offer(SettingsIntent.ChangeQuranFontSize(progress))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        binding.surahSelectorCardView.setOnClickListener {
            showSurahNamePopup(surahNameCalligraphies)
        }
        binding.ayaSelectorCardView.setOnClickListener {
            showAyaCalligraphyPopup(ayaCalligraphies)
        }
    }

    override fun onDestroyView(view: View) {
        ayaCalligraphiesView?.dismiss()
        ayaCalligraphiesView = null

        surahNameCalligraphiesView?.dismiss()
        surahNameCalligraphiesView = null

        super.onDestroyView(view)
    }

    override fun render(state: SettingsState) {
        renderLoading(state.base)
        renderError(state.base)
        binding.skFontSize.progress = state.quranTextFontSize

        binding.tvCurrentSurahName.text = state.selectedSurahNameCalligraphy?.name
        binding.currentAyaCalligraphyTextView.text = state.selectedAyaCalligraphy?.name

        ayaCalligraphies = state.ayaCalligraphies
        surahNameCalligraphies = state.surahNameCalligraphies
    }

    override fun intents(): Flow<SettingsIntent> =
        listOf(
            flowOf(
                SettingsIntent.Initial
            ),
            intentChannel.asFlow()
        ).merge()

    private fun showAyaCalligraphyPopup(calligraphies: List<CalligraphyUIModel>) {
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val listView = EpoxyAsyncRecyclerView(activity!!)
        listView.withModelsAsync {
            calligraphies.forEach {
                calligraphyView {
                    id(it.id)
                    calligraphy(it)
                    callback {
                        intentChannel.offer(SettingsIntent.NewAyaCalligraphy(it))
                        ayaCalligraphiesView!!.dismiss()
                    }
                }
            }
        }
        ayaCalligraphiesView = PopupWindow(listView, width, height).apply {
            setBackgroundDrawable(ColorDrawable(Color.WHITE))
            isOutsideTouchable = true
            showAsDropDown(binding.ayaSelectorCardView)
        }
    }

    private fun showSurahNamePopup(calligraphies: List<CalligraphyUIModel>) {
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val listView = EpoxyAsyncRecyclerView(activity!!)
        listView.withModelsAsync {
            calligraphies.forEach {
                calligraphyView {
                    id(it.id)
                    calligraphy(it)
                    callback {
                        intentChannel.offer(SettingsIntent.NewSurahNameCalligraphySelected(it))
                        surahNameCalligraphiesView!!.dismiss()
                    }
                }
            }
        }
        surahNameCalligraphiesView = PopupWindow(listView, width, height).apply {
            setBackgroundDrawable(ColorDrawable(Color.WHITE))
            isOutsideTouchable = true
            showAsDropDown(binding.surahSelectorCardView)
        }
    }
}
