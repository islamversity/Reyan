package com.islamversity.quran_home.feature.home

import com.islamversity.core.FlowBlock
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.core.ofType
import com.islamversity.navigation.Navigator
import com.islamversity.navigation.Screens
import com.islamversity.navigation.model.SearchLocalModel
import com.islamversity.navigation.navigateTo
import kotlinx.coroutines.flow.map

class QuranHomeProcessor(
    navigator: Navigator
) : BaseProcessor<QuranHomeIntent, QuranHomeResult>() {
    override fun transformers(): List<FlowBlock<QuranHomeIntent, QuranHomeResult>> =
        listOf(itemClick, settingsClicked, searchClicked)

    private val itemClick: FlowBlock<QuranHomeIntent, QuranHomeResult> = {
        ofType<QuranHomeIntent.SelectTab>()
            .map { QuranHomeResult.Success(it.position) }
    }

    private val searchClicked: FlowBlock<QuranHomeIntent, QuranHomeResult> = {
        ofType<QuranHomeIntent.SearchClicked>()
            .map {
                //TODO ADD TRANSITION
                Screens.Search(SearchLocalModel())
            }
            .navigateTo(navigator)
    }

    private val settingsClicked: FlowBlock<QuranHomeIntent, QuranHomeResult> = {
        ofType<QuranHomeIntent.SettingsClicked>()
            .map {
                Screens.Settings()
            }
            .navigateTo(navigator)
    }
}