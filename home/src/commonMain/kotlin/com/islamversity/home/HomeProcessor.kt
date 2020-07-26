package com.islamversity.home

import com.islamversity.core.*
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.navigation.NavigationAnimation
import com.islamversity.navigation.Navigator
import com.islamversity.navigation.Screens
import com.islamversity.navigation.model.SearchLocalModel
import com.islamversity.navigation.navigateTo
import kotlinx.coroutines.flow.*

class HomeProcessor(
    private val navigator: Navigator
) : BaseProcessor<HomeIntent, HomeResult>() {

    override fun transformers(): List<FlowBlock<HomeIntent, HomeResult>> = listOf(
            searchClicked
        )

    private val searchClicked:
            Flow<HomeIntent>.() -> Flow<HomeResult> = {
        ofType<HomeIntent.SearchClicks>()
            .map {
                Screens.Search(
                    SearchLocalModel(
                        it.backTransName,
                        it.textTransName
                    ),
                    NavigationAnimation.ArcFadeMove(
                        it.backTransName,
                        it.textTransName
                    ),
                    NavigationAnimation.ArcFadeMove(
                        it.backTransName,
                        it.textTransName
                    )
                )
            }
            .navigateTo(navigator)
    }
}