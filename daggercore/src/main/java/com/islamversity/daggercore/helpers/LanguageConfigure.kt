package com.islamversity.daggercore.helpers

import com.bluelinelabs.conductor.Controller
import java.util.*

data class LanguageLocale(
    val locale: Locale,
    val localeName: String
)

interface LanguageConfigure {
    fun getSupportedLanguages(): List<LanguageLocale>

    fun getCurrentLocale(): LanguageLocale

    fun setNewLanguage(locale: Locale)
}

val Controller.languageConfigure
    get() = router.activity!! as LanguageConfigure

val ENGLISH_LOCALE = Locale.ENGLISH
val ARABIC_LOCALE = Locale("ar")
val FARSI_LOCALE = Locale("fa")
val DEFAULT_LANGUAGE_LOCALE = ENGLISH_LOCALE


