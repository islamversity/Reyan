package com.islamversity.quran_home.feature.juz

import com.islamversity.core.FlowBlock
import com.islamversity.core.mvi.BasePresenter
import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.core.notOfType
import com.islamversity.core.ofType
import kotlinx.coroutines.flow.take

class JuzListPresenter(
    processor: MviProcessor<JuzListIntent, JuzListResult>
) : BasePresenter<JuzListIntent, JuzListState, JuzListResult>(
    processor,
    JuzListState.idle()
) {
    override fun filterIntent(): List<FlowBlock<JuzListIntent, JuzListIntent>> =
        listOf<FlowBlock<JuzListIntent, JuzListIntent>>(
            {
                this.ofType<JuzListIntent.Initial>().take(1)
            },
            {
                notOfType(JuzListIntent.Initial::class)
            })

    override fun reduce(preState: JuzListState, result: JuzListResult) =
        when (result) {
            is JuzListResult.Error ->
                preState.copy(base = BaseState.withError(result.err))
            JuzListResult.Loading ->
                preState.copy(base = BaseState.loading())
            is JuzListResult.JuzSuccess ->
                preState.copy(
                    base = BaseState.stable(),
                    juzList = result.list
                )
        }
}