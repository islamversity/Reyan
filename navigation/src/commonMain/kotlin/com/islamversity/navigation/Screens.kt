package com.islamversity.navigation

import com.islamversity.navigation.model.SearchLocalModel
import com.islamversity.navigation.model.SearchLocalModel.Companion.EXTRA_SEARCH
import com.islamversity.navigation.model.SurahLocalModel
import com.islamversity.navigation.model.SurahLocalModel.Companion.EXTRA_SURAH_DETAIL
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal val jsonParser = Json {
    prettyPrint = false
    encodeDefaults = false
    coerceInputValues = true
}

sealed class Screens(
    val name: String,
    val extras: Pair<String, String>? = null,
    val pushAnimation: NavigationAnimation? = null,
    val popAnimation: NavigationAnimation? = null
) {

    /**
     * Screens supporting dynamic loading (Dynamic Feature Module) should implement this interface
     */
    interface Dynamic {
        val module: String
    }

    internal object Test : Screens("com.islamversity.navigation.TestController")

    object Home : Screens("com.islamversity.quran_home.feature.home.QuranHomeView")
    object OnBoarding : Screens("com.islamversity.quran_home.feature.onboarding.OnBoardingView")

    class Search(
        model: SearchLocalModel,
        pushAnimation: NavigationAnimation? = null,
        popAnimation: NavigationAnimation? = null
    ) : Screens(
        "com.islamversity.search.view.SearchView",
        EXTRA_SEARCH to jsonParser.encodeToString(model),
        pushAnimation,
        popAnimation
    )

    class Surah(
        model: SurahLocalModel,
        pushAnimation: NavigationAnimation? = null,
        popAnimation: NavigationAnimation? = null
    ) : Screens(
        "com.islamversity.surah.view.SurahView",
        EXTRA_SURAH_DETAIL to jsonParser.encodeToString(model),
        pushAnimation,
        popAnimation
    )

    class Settings(
    ) : Screens(
        "com.islamversity.settings.SettingsView"
    )
}
