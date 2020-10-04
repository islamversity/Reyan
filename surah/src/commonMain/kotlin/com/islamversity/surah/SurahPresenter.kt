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
        }
}