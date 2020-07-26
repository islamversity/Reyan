package com.islamversity.home

import com.islamversity.core.FlowBlock
import com.islamversity.core.mvi.BasePresenter
import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.core.noOfType
import com.islamversity.core.ofType
import com.islamversity.core.publish
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take

class HomePresenter(
    processor: MviProcessor<HomeIntent, HomeResult>
) : BasePresenter<HomeIntent, HomeState, HomeResult>(processor, HomeState.start()) {

    override fun filterIntent(): List<FlowBlock<HomeIntent, HomeIntent>> =
        listOf<FlowBlock<HomeIntent, HomeIntent>>(
            {
                ofType<HomeIntent.Initial>().take(1)
            },
            {
                noOfType(HomeIntent.Initial::class)
            }
        )

    override fun reduce(preState: HomeState, result: HomeResult): HomeState = when (result) {
        is HomeResult.Error ->
            preState.copy(base = BaseState.withError(result.err))
        HomeResult.Loading ->
            preState.copy(base = BaseState.loading())
        HomeResult.LastStable ->
            preState.copy(base = BaseState.stable())
    }
}