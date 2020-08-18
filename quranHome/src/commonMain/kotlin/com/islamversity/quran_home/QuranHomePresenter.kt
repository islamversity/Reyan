package com.islamversity.quran_home

import com.islamversity.core.FlowBlock
import com.islamversity.core.mvi.BasePresenter
import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.core.notOfType
import com.islamversity.core.ofType
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlin.reflect.typeOf

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

    override fun reduce(preState: QuranHomeState, result: QuranHomeResult) =
        when (result) {
            is QuranHomeResult.Error ->
                preState.copy(base = BaseState.withError(result.err))
            QuranHomeResult.Loading ->
                preState.copy(base = BaseState.loading())
            is QuranHomeResult.SurahsSuccess ->
                preState.copy(
                    base = BaseState.stable(),
                    surahList = result.list
                )
            is QuranHomeResult.JuzSuccess ->
                preState.copy(
                    base = BaseState.stable(),
                    juzList = result.list
                )
        }
}