package com.islamversity.surah

import com.islamversity.core.FlowBlock
import com.islamversity.core.mvi.BasePresenter
import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.core.notOfType
import com.islamversity.core.ofType
import com.islamversity.surah.settings.SurahSettingsIntent
import com.islamversity.surah.settings.SurahSettingsResult
import com.islamversity.surah.settings.SurahSettingsState
import kotlinx.coroutines.flow.take

class SurahPresenter(
    processor: MviProcessor<SurahIntent, SurahResult>
) : BasePresenter<SurahIntent, SurahState, SurahResult>(
    processor,
    SurahState.idle()
) {

    override fun filterIntent(): List<FlowBlock<SurahIntent, SurahIntent>> = listOf(
        {
            ofType<SurahIntent.Initial>().take(1)
        },
        {
            notOfType(SurahIntent.Initial::class)
        },
    )

    override fun reduce(preState: SurahState, result: SurahResult): SurahState =
        when (result) {
            SurahResult.LastStable ->
                preState.copy(
                    base = BaseState.stable(),
                    scrollToAya = null
                )
            is SurahResult.Error ->
                preState.copy(
                    base = BaseState.withError(result.err)
                )
            SurahResult.Loading ->
                preState.copy(
                    base = BaseState.loading()
                )
            is SurahResult.Items ->
                preState.copy(
                    items = result.items
                )
            is SurahResult.ShowAyaNumber ->
                preState.copy(
                    scrollToAya = ScrollToAya(result.id, result.orderID, result.position)
                )

            is SurahResult.Settings ->
                preState.copy(settingsState = settingsReducer(preState.settingsState, result))
            is SurahResult.MainAyaFontSize ->
                preState.copy(mainAyaFontSize = result.size)
            is SurahResult.TranslationFontSize ->
                preState.copy(translationFontSize = result.size)
        }

    private val settingsReducer : (SurahSettingsState, SurahResult.Settings) -> SurahSettingsState = { preState, result ->
        when (result) {
            is SurahResult.Settings.TranslationCalligraphies ->
                preState.copy(ayaCalligraphies = result.list)
            is SurahResult.Settings.QuranFontSize ->
                preState.copy(quranTextFontSize = result.fontSize)
            is SurahResult.Settings.TranslateFontSize ->
                preState.copy(translateTextFontSize = result.fontSize)
            is SurahResult.Settings.FirstAyaTranslationCalligraphy ->
                preState.copy(selectedFirstTranslationAyaCalligraphy = result.calligraphy)
            is SurahResult.Settings.SecondAyaTranslationCalligraphy ->
                preState.copy(selectedSecondTranslationAyaCalligraphy = result.calligraphy)
        }

    }
}