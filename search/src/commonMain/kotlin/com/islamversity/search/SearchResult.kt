package com.islamversity.search

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviResult
import com.islamversity.domain.model.sora.SoraUIModel

sealed class SearchResult : MviResult {

    object LastStable : SearchResult()

    object Loading : SearchResult()
    data class Data(val items : List<SoraUIModel>) : SearchResult()
    data class Error(val err : BaseState.ErrorState) : SearchResult()
}