package com.islamversity.quran_home.feature.home

import com.islamversity.core.FlowBlock
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.core.ofType
import kotlinx.coroutines.flow.map

class QuranHomeProcessor() : BaseProcessor<QuranHomeIntent, QuranHomeResult>() {
    override fun transformers(): List<FlowBlock<QuranHomeIntent, QuranHomeResult>> =
        listOf(itemClick)


    private val itemClick: FlowBlock<QuranHomeIntent, QuranHomeResult> = {
        ofType<QuranHomeIntent.SelectTab>()
            .map { QuranHomeResult.Success(it.position) }
    }
}