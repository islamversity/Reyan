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

    object OnBoarding : Screens("com.islamversity.quran_home.feature.onboarding.OnBoardingView")
    class Home : Screens(name) {
        companion object{
            const val name = "com.islamversity.quran_home.feature.home.QuranHomeView" //"com.islamversity.quran_home.view.HomeView"
            const val url = "/home"
        }
    }

    class Search(
        model: SearchLocalModel,
        pushAnimation: NavigationAnimation? = null,
        popAnimation: NavigationAnimation? = null
    ) : Screens(
        name,
        EXTRA_SEARCH to jsonParser.encodeToString(model),
        pushAnimation,
        popAnimation
    ){
        companion object{
            const val name = "com.islamversity.search.view.SearchView"
            const val url = "/search"
        }
    }

    class Surah(
        model: SurahLocalModel,
        pushAnimation: NavigationAnimation? = null,
        popAnimation: NavigationAnimation? = null
    ) : Screens(
        name,
        EXTRA_SURAH_DETAIL to jsonParser.encodeToString(model),
        pushAnimation,
        popAnimation
    ){
        companion object{
            const val name = "com.islamversity.surah.view.SurahView"
            const val url = "/surah"
        }
    }

    class Settings
        : Screens(name)
    {
        companion object{
            const val name ="com.islamversity.settings.SettingsView"
            const val url = "/settings"
        }
    }
}
