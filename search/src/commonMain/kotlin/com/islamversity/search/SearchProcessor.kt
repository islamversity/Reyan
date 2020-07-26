package com.islamversity.search

import com.islamversity.core.*
import com.islamversity.navigation.Navigator
import com.islamversity.core.mvi.BaseProcessor

class SearchProcessor(
    navigator: Navigator
) : BaseProcessor<SearchIntent, SearchResult>() {

    override fun transformers(): List<FlowBlock<SearchIntent, SearchResult>> = listOf()
}