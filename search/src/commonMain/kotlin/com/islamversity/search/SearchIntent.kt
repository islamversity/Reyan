package com.islamversity.search

import com.islamversity.core.mvi.MviIntent
import com.islamversity.search.model.SurahRowActionModel

sealed class SearchIntent : MviIntent {
    object Initial : SearchIntent()

    class Search(
        val query: String
    ) : SearchIntent()

    data class ItemClick(
        val action : SurahRowActionModel
    ) : SearchIntent()
}