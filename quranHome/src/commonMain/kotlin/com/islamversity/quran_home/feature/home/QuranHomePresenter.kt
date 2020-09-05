package com.islamversity.quran_home.feature.home

import com.islamversity.core.FlowBlock
import com.islamversity.core.mvi.BasePresenter
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.core.notOfType
import com.islamversity.core.ofType
import kotlinx.coroutines.flow.take

class QuranHomePresenter(
    processor: MviProcessor<QuranHomeIntent, QuranHomeResult>
) : BasePresenter<QuranHomeIntent, QuranHomeState, QuranHomeResult>(
    processor,
    QuranHomeState.idle()
) {
    override fun filterIntent(): List<FlowBlock<QuranHomeIntent, QuranHomeIntent>> =
        listOf<FlowBlock<QuranHomeIntent, QuranHomeIntent>>(
            {
                this.ofType<QuranHomeIntent.Initial>().take(1)
            },
            {
                notOfType(QuranHomeIntent.Initial::class)
            })

    override fun reduce(preState: QuranHomeState, result: QuranHomeResult)  = TODO()
}