package com.islamversity.quran_home.feature.surah

import com.islamversity.core.FlowBlock
import com.islamversity.core.mvi.BasePresenter
import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.core.notOfType
import com.islamversity.core.ofType
import kotlinx.coroutines.flow.take

class SurahListPresenter(
    processor: MviProcessor<SurahListIntent, SurahListResult>
) : BasePresenter<SurahListIntent, SurahListState, SurahListResult>(
    processor,
    SurahListState.idle()
) {
    override fun filterIntent(): List<FlowBlock<SurahListIntent, SurahListIntent>> =
        listOf<FlowBlock<SurahListIntent, SurahListIntent>>(
            {
                this.ofType<SurahListIntent.Initial>().take(1)
            },
            {
                notOfType(SurahListIntent.Initial::class)
            })

    override fun reduce(preState: SurahListState, result: SurahListResult) =
        when (result) {
            is SurahListResult.Error ->
                preState.copy(base = BaseState.withError(result.err))
            SurahListResult.Loading ->
                preState.copy(base = BaseState.loading())
            is SurahListResult.SurahsSuccess ->
                preState.copy(
                    base = BaseState.stable(),
                    surahList = result.list
                )
        }
}