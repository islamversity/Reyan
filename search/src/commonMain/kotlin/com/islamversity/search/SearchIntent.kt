package com.islamversity.search

import com.islamversity.core.mvi.MviIntent

sealed class SearchIntent : MviIntent {
    object Initial : SearchIntent()

    class Search(
        val query: String
    ) : SearchIntent()

    data class NextPage(
        val query: String,
        val totalItemsCount: Int,
        val page: Int
    ) : SearchIntent()
}