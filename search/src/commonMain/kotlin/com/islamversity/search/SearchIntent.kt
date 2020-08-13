package com.islamversity.search

import com.islamversity.core.mvi.MviIntent
import com.islamversity.domain.model.sora.SoraRowActionModel
import com.islamversity.domain.model.sora.SoraUIModel

sealed class SearchIntent : MviIntent {

    object Initial : SearchIntent()

    class Search(
        val query: String
    ) : SearchIntent()

//    data class NextPage(
//        val query: String,
//        val totalItemsCount: Int,
//        val page: Int
//    ) : SearchIntent()

    data class ItemClick(
        val item : SoraRowActionModel
    ) : SearchIntent()
}