package com.islamversity.surah.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import com.airbnb.epoxy.EpoxyVisibilityTracker
import com.airbnb.epoxy.VisibilityState
import com.islamversity.base.CoroutineView
import com.islamversity.base.ext.setHidable
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.helpers.LanguageConfigure
import com.islamversity.daggercore.helpers.languageConfigure
import com.islamversity.navigation.model.SurahLocalModel
import com.islamversity.navigation.model.fromData
import com.islamversity.navigation.requireArgs
import com.islamversity.surah.R
import com.islamversity.surah.SurahIntent
import com.islamversity.surah.SurahState
import com.islamversity.surah.databinding.ViewSurahBinding
import com.islamversity.surah.di.DaggerSurahComponent
import com.islamversity.surah.model.AyaUIModel
import com.islamversity.surah.model.SurahHeaderUIModel
import com.islamversity.surah.settings.SurahSettingsState
import com.islamversity.surah.view.settings.OnSettings
import com.islamversity.surah.view.settings.SurahSettingsView
import com.islamversity.surah.view.utils.BuildFinishedScroller
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class SurahView(
    bundle: Bundle
) : CoroutineView<ViewSurahBinding, SurahState, SurahIntent>(bundle) {

    private lateinit var numberFormat: NumberFormat
    private var settingsDialog: SurahSettingsView? = null
    private var settingsState: SurahSettingsState = SurahSettingsState()
    private val onSettings: OnSettings = { setting ->
        intents.tryEmit(setting)
    }

    private val extraLocal: SurahLocalModel = requireArgs(SurahLocalModel.EXTRA_SURAH_DETAIL)

    @Inject
    override lateinit var presenter: MviPresenter<SurahIntent, SurahState>

    override fun bindView(inflater: LayoutInflater, container: ViewGroup): ViewSurahBinding =
        ViewSurahBinding.inflate(inflater, container, false)

    override fun injectDependencies(core: CoreComponent) {
        DaggerSurahComponent
            .builder()
            .bindRouter(router)
            .coreComponent(core)
            .build()
            .inject(this)
    }

    override fun beforeBindingView(binding: ViewSurahBinding) {
        numberFormat = NumberFormat.getInstance(languageConfigure.getCurrentLocale().locale)
        binding.ivBack.setOnClickListener { router.handleBack() }
        binding.fabUp.setOnClickListener { binding.ayaList.scrollToPosition(0) }
        binding.ayaList.setHidable(binding.fabUp, binding.tvSurahName)
        binding.settings.setOnClickListener { openSettingsDialog(it.context) }
        binding.tvSurahName.text = getToolbarName(binding.root.context, extraLocal)
    }

    private fun getToolbarName(context: Context, model: SurahLocalModel): String =
        when (model) {
            is SurahLocalModel.FullSurah -> model.surahName
            is SurahLocalModel.FullJuz -> context.getString(R.string.home_tab_parts) + " " + numberFormat.format(model.juzOrder)
        }

    private fun openSettingsDialog(context: Context) {
        settingsDialog = SurahSettingsView(context, settingsState)
        settingsDialog!!.onSettings = onSettings
        settingsDialog!!.setOnCancelListener { clearSettings() }
        settingsDialog!!.show()
    }

    override fun onDestroyView(view: View) {
        binding.ayaList.clearOnScrollListeners()
        super.onDestroyView(view)
    }

    private fun clearSettings() {
        settingsDialog?.dismiss()
        settingsDialog?.onSettings = null
        settingsDialog = null
    }

    override fun intents(): Flow<SurahIntent> =
        flowOf(
            SurahIntent.Initial(extraLocal)
        )

    override fun render(state: SurahState) {
        renderLoading(state.base)
        renderError(state.base)

        if (state.closeScreen) {
            router.popController(this)
        }

        settingsState = state.settingsState

        binding.ayaList.withModelsAsync {
            addModelBuildListener {

            }
            state.items.forEach {
                if (it is SurahHeaderUIModel) {
                    surahHeader {
                        id(it.rowId)
                        model(
                            it
                        )
                    }
                }

                if (it is AyaUIModel) {
                    ayaView {
                        id(it.rowId)
                        model(it)
                        mainAyaFontSize(state.mainAyaFontSize)
                        translationFontSize(state.translationFontSize)
                        toolbarVisible(state.settingsState.ayaToolbarVisible)
                        listener(::ayaClicks)
                    }
                }
            }

            if (state.scrollToAya != null) {
                addModelBuildListener(
                    BuildFinishedScroller(
                        state.scrollToAya.position,
                        this,
                        binding.ayaList
                    )
                )
            }
        }
    }

    private fun ayaClicks(actions: SurahIntent.AyaActions) {
        when (actions) {
            is SurahIntent.AyaActions.Share -> {
                shareAya(actions)
            }
        }
    }

    private fun shareAya(actions: SurahIntent.AyaActions.Share) {
        ShareCompat.IntentBuilder.from(activity!!)
            .setType("text/plain")
            .setText(ayaToSharedMessage(actions.aya))
            .startChooser()
    }

    private fun ayaToSharedMessage(model: AyaUIModel): String = buildString {
        append(binding.tvSurahName.text)
        append(" (")
        append(numberFormat.format(model.order))
        append(") :")
        appendLine()
        appendLine()
        append(model.content)
        appendLine()

        if (model.translation1 != null) {
            appendLine()
            append(model.translation1)
        }
        if (model.translation2 != null) {
            appendLine()
            append(model.translation2)
        }
    }
}

