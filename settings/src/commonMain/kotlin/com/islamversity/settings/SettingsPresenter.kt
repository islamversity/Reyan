package com.islamversity.settings

import com.islamversity.core.FlowBlock
import com.islamversity.core.mvi.BasePresenter
import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.core.notOfType
import com.islamversity.core.ofType
import kotlinx.coroutines.flow.take

class SettingsPresenter(
    processor: MviProcessor<SettingsIntent, SettingsResult>
) : BasePresenter<SettingsIntent, SettingsState, SettingsResult>(
    processor,
    SettingsState.idle()
) {
    override fun filterIntent(): List<FlowBlock<SettingsIntent, SettingsIntent>> =
        listOf(
            {
                ofType<SettingsIntent.Initial>().take(1)
            },
            {
                notOfType(SettingsIntent.Initial::class)
            }
        )

    override fun reduce(preState: SettingsState, result: SettingsResult): SettingsState =
        when (result) {
            SettingsResult.LastStable ->
                preState.copy(
                    base = BaseState.stable()
                )
            is SettingsResult.Error ->
                preState.copy(
                    base = BaseState.withError(result.err)
                )
            is SettingsResult.SurahNameCalligraphies ->
                preState.copy(
                    surahNameCalligraphies = result.list
                )
            is SettingsResult.AyaCalligraphies ->
                preState.copy(
                    ayaCalligraphies = result.list
                )
            is SettingsResult.AyaFontSize ->
                preState.copy(
                    quranTextFontSize = result.fontSize.toInt()
                )
            is SettingsResult.SurahCalligraphy ->
                preState.copy(
                    selectedSurahNameCalligraphy = result.calligraphy
                )
            is SettingsResult.AyaCalligraphy ->
                preState.copy(
                    selectedAyaCalligraphy = result.calligraphy
                )
        }
}
