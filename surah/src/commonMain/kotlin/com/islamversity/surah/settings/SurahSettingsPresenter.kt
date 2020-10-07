package com.islamversity.surah.settings

import com.islamversity.core.FlowBlock
import com.islamversity.core.mvi.BasePresenter
import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.core.notOfType
import com.islamversity.core.ofType
import kotlinx.coroutines.flow.take

class SurahSettingsPresenter(
    processor: MviProcessor<SurahSettingsIntent, SurahSettingsResult>
) : BasePresenter<SurahSettingsIntent, SurahSettingsState, SurahSettingsResult>(
    processor,
    SurahSettingsState.idle()
) {
    override fun filterIntent(): List<FlowBlock<SurahSettingsIntent, SurahSettingsIntent>> =
        listOf(
            {
                ofType<SurahSettingsIntent.Initial>().take(1)
            },
            {
                notOfType(SurahSettingsIntent.Initial::class)
            }
        )

    override fun reduce(preState: SurahSettingsState, result: SurahSettingsResult): SurahSettingsState =
        when (result) {
            SurahSettingsResult.LastStable ->
                preState.copy(
                    base = BaseState.stable()
                )
            is SurahSettingsResult.Error ->
                preState.copy(
                    base = BaseState.withError(result.err)
                )
            is SurahSettingsResult.SurahNameCalligraphies ->
                preState.copy(
                    surahNameCalligraphies = result.list
                )
            is SurahSettingsResult.AyaCalligraphies ->
                preState.copy(
                    ayaCalligraphies = result.list
                )
            is SurahSettingsResult.QuranFontSize ->
                preState.copy(
                    quranTextFontSize = result.fontSize
                )
            is SurahSettingsResult.TranslateFontSize ->
                preState.copy(
                    translateTextFontSize = result.fontSize
                )
            is SurahSettingsResult.SurahCalligraphy ->
                preState.copy(
                    selectedSurahNameCalligraphy = result.calligraphy
                )
            is SurahSettingsResult.FirstAyaTranslationCalligraphy ->
                preState.copy(
                    selectedAyaCalligraphy = result.calligraphy
                )
        }
}
