package com.islamversity.navigation

import com.islamversity.navigation.model.SearchLocalModel
import com.islamversity.navigation.model.SearchLocalModel.Sinker.EXTRA_SEARCH
import com.islamversity.navigation.model.SoraDetailLocalModel
import com.islamversity.navigation.model.SoraDetailLocalModel.Sinker.EXTRA_SORA_DETAIL

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

    class SoraDetail(
        model: SoraDetailLocalModel,
        pushAnimation: NavigationAnimation? = null,
        popAnimation: NavigationAnimation? = null
    ) : Screens(
        "com.islamversity.sora_detail.view.SoraDetailView",
        EXTRA_SORA_DETAIL to SoraDetailLocalModel.toByteArray(model),
        pushAnimation,
        popAnimation
    ), Dynamic {
        override val module: String
            get() = "sora_detail"
    }
}