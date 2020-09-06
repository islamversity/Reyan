package com.islamversity.surah

import com.islamversity.core.mvi.BasePresenter
import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviProcessor

class SurahPresenter(
    processor: MviProcessor<SurahIntent, SurahResult>
) : BasePresenter<SurahIntent, SurahState, SurahResult>(
    processor,
    SurahState.idle()
) {

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
        }
}