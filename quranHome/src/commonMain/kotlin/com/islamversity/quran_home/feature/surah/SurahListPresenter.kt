package com.islamversity.quran_home.feature.surah

import com.islamversity.core.FlowBlock
import com.islamversity.core.Logger
import com.islamversity.core.mvi.BasePresenter
import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.core.notOfType
import com.islamversity.core.ofType
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take

class SurahListPresenter(
    processor: MviProcessor<SurahListIntent, SurahListResult>
) : BasePresenter<SurahListIntent, SurahListState, SurahListResult>(
    processor,
    SurahListState.idle()
) {
    override fun filterIntent(): List<FlowBlock<SurahListIntent, SurahListIntent>> =
        listOf(
            {
                this.onEach {
                    Logger.log {
                        "GetSurah : filterIntent : ofType"  + it.toString()
                    }
                }
                .ofType<SurahListIntent.Initial>().take( 1)
            },
            {
                notOfType(SurahListIntent.Initial::class)
                .onEach {
                    Logger.log {
                        "GetSurah : filterIntent : notOfType"  + it.toString()
                    }
                }
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