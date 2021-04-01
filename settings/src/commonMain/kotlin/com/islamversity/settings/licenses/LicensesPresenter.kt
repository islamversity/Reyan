package com.islamversity.settings.licenses

import com.islamversity.core.FlowBlock
import com.islamversity.core.mvi.BasePresenter
import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.core.notOfType
import com.islamversity.core.ofType
import kotlinx.coroutines.flow.take

class LicensesPresenter(
    processor: MviProcessor<LicensesIntent, LicensesResult>
) : BasePresenter<LicensesIntent, LicensesState, LicensesResult>(
    processor,
    LicensesState.idle()
) {
    override fun filterIntent(): List<FlowBlock<LicensesIntent, LicensesIntent>> =
        listOf(
            {
                ofType<LicensesIntent.Initial>().take(1)
            },
            {
                notOfType(LicensesIntent.Initial::class)
            }
        )

    override fun reduce(preState: LicensesState, result: LicensesResult): LicensesState =
        when (result) {
            is LicensesResult.LastStable ->
                preState.copy(
                    base = BaseState.stable()
                )
            is LicensesResult.Error ->
                preState.copy(
                    base = BaseState.withError(result.err)
                )
            is LicensesResult.LicensesList ->
                preState.copy(licenses = result.list)
        }
}