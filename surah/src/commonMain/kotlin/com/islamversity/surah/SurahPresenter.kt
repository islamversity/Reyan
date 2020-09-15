package com.islamversity.surah

import com.islamversity.core.FlowBlock
import com.islamversity.core.mvi.BasePresenter
import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.core.notOfType
import com.islamversity.core.ofType
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
        }
    )

    override fun reduce(preState: SurahState, result: SurahResult): SurahState =
        when (result) {
            SurahResult.LastStable ->
                preState.copy(
                    base = BaseState.stable()
                )
            is SurahResult.Error ->
                preState.copy(
                    base = BaseState.withError(result.err)
                )
            SurahResult.Loading ->
                preState.copy(
                    base = BaseState.loading()
                )
            is SurahResult.MainAyasLoaded ->
                preState.copy(
                    ayas = result.ayas,
                    mainAyaFontSize = result.fontSize
                )
            SurahResult.Bismillah.Hide ->
                preState.copy(
                    showBismillah = false
                )
            SurahResult.Bismillah.Show ->
                preState.copy(
                    showBismillah = true
                )
            is SurahResult.Bismillah.Content ->
                preState.copy(
                    bismillah = result.value
                )
            SurahResult.SurahNotFound ->
                preState.copy(
                    closeScreen = true
                )
        }
}