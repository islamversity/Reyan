package com.islamversity.settings

import com.islamversity.core.mvi.BasePresenter
import com.islamversity.core.mvi.MviProcessor

class LicensesPresenter(
    processor: MviProcessor<LicensesIntent, LicensesResult>
) : BasePresenter<LicensesIntent, LicensesState, LicensesResult>(
    processor,
    LicensesState.idle()
) {
    override fun reduce(preState: LicensesState, result: LicensesResult): LicensesState {
        TODO("Not yet implemented")
    }

}