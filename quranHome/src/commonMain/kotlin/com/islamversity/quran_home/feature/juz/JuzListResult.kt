package com.islamversity.quran_home.feature.juz

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviResult
import com.islamversity.quran_home.feature.juz.model.JozUIModel

sealed class JuzListResult : MviResult {
    data class Error(val err: BaseState.ErrorState) : JuzListResult()
    object Loading : JuzListResult()

    data class JuzSuccess(val list : List<JozUIModel>) : JuzListResult()
}