package com.islamversity.surah.view.settings

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.islamversity.core.Logger
import com.islamversity.core.Severity
import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.coreComponent
import com.islamversity.domain.model.QuranReadFontSize
import com.islamversity.surah.databinding.DialogSettingsBinding
import com.islamversity.surah.di.settings.DaggerSurahSettingsComponent
import com.islamversity.surah.settings.SurahSettingsIntent
import com.islamversity.surah.settings.SurahSettingsPresenter
import com.islamversity.surah.settings.SurahSettingsState
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SurahSettingsView(context: Context) : Dialog(context), CoroutineScope by MainScope() {
    private var binding: DialogSettingsBinding? = null
    private val intentChannel = BroadcastChannel<SurahSettingsIntent>(Channel.BUFFERED)
    private var ayaInit = false
    private var translateInit = false

    @Inject
    lateinit var presenter: SurahSettingsPresenter
    private lateinit var coreComponent: CoreComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogSettingsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (!::coreComponent.isInitialized) {
            coreComponent = context.applicationContext!!.coreComponent()
        }
        DaggerSurahSettingsComponent.factory().create(coreComponent).inject(this)

        bind()

        binding!!.ayaFontSizeSeekBar.onSeekChangeListener = object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams) {
                intentChannel.offer(SurahSettingsIntent.ChangeQuranFontSize(seekParams.progress))
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
                intentChannel.offer(SurahSettingsIntent.ChangeTranslateFontSize(seekParams.progress))
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

    }

    private fun bind() {
        intents()
            .newIntents()

        presenter
            .states()
            .catch {
                Logger.log(Severity.Error, "SurahSettings", it, "presenter state flow exception caught: ${it.localizedMessage}")
            }
            .onEach {
                render(it)
            }
            .launchIn(this)
    }

    private fun render(state: SurahSettingsState) {
        if (state.quranTextFontSize != QuranReadFontSize.DEFAULT.size && !ayaInit) {
            ayaInit = true
            binding!!.ayaFontSizeSeekBar.setProgress(state.quranTextFontSize.toFloat())
        }
        if (state.quranTextFontSize != QuranReadFontSize.DEFAULT.size && !translateInit) {
            translateInit = true
            binding!!.transLateFontSizeSeekBar.setProgress(state.translateTextFontSize.toFloat())
        }
    }

    private fun Flow<SurahSettingsIntent>.newIntents() =
        catch {
            Logger.log(Severity.Error, "SurahSettings", it, "view intents flow exception caught: ${it.localizedMessage}")
        }.onEach {
            presenter.processIntents(it)
        }.launchIn(this@SurahSettingsView)


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.close()
        binding = null
    }

    private fun intents(): Flow<SurahSettingsIntent> =
        listOf(
            flowOf(SurahSettingsIntent.Initial),
            intentChannel.asFlow()
        ).merge()


}