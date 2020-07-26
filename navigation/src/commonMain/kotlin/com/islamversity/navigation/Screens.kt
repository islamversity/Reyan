package com.islamversity.navigation

import com.islamversity.navigation.model.SearchLocalModel
import com.islamversity.navigation.model.SearchLocalModel.Sinker.EXTRA_SEARCH

sealed class Screens(
    val name: String,
    val extras: Pair<String, ByteArray>? = null,
    val pushAnimation: NavigationAnimation? = null,
    val popAnimation: NavigationAnimation? = null
) {

    interface Dynamic {
        val module: String
    }

    internal object Test : Screens("com.islamversity.navigation.TestController")

    object Home : Screens("com.islamversity.home.view.HomeView")

    class Search(
        model: SearchLocalModel,
        pushAnimation: NavigationAnimation? = null,
        popAnimation: NavigationAnimation? = null
    ) : Screens(
        "com.islamversity.search.view.SearchView",
        EXTRA_SEARCH to SearchLocalModel.toByteArray(model),
        pushAnimation,
        popAnimation
    ), Dynamic {
        override val module: String
            get() = "search"
    }
}