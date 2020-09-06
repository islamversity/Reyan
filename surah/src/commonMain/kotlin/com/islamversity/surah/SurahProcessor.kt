package com.islamversity.surah

import com.islamversity.core.FlowBlock
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.navigation.Navigator

class SurahProcessor(
    private val navigator: Navigator,
) : BaseProcessor<SurahIntent, SurahResult>() {

    override fun transformers(): List<FlowBlock<SurahIntent, SurahResult>> = emptyList()
}