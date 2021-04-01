package com.islamversity.settings.licenses

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState
import com.islamversity.settings.models.LicensesUIModel

data class LicensesState(
    override val base: BaseState,
    val licenses: List<LicensesUIModel>
) : BaseViewState {
    companion object {
        fun idle() = LicensesState(BaseState.stable(), emptyList())
    }
}