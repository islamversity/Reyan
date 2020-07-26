package com.islamversity.search

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviResult

sealed class SearchResult : MviResult {
    object LastStable : SearchResult()
    data class Error(val err : BaseState.ErrorState) : SearchResult()
}