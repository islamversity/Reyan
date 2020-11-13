package com.islamversity.domain.model

inline class Language(val lang: String)
inline class CalligraphyId(val id: String)
inline class CalligraphyName(val name: String)

inline class CalligraphyCode(val code: String)

data class Calligraphy(
    val id: CalligraphyId,
    val language: Language,
    val name: CalligraphyName?,
    val friendlyName : String
) {
    val code: CalligraphyCode =
        if (name == null) {
            CalligraphyCode(language.lang)
        } else {
            CalligraphyCode("${language.lang}_${name.name}")
        }

    companion object{
        val EMPTY = Calligraphy(
            CalligraphyId(""),
            Language(""),
            null,
            "",
        )
    }
}

fun Calligraphy.toEntity() = com.islamversity.db.model.Calligraphy(code.code)
fun CalligraphyId.toEntity() = com.islamversity.db.model.CalligraphyId(id)

sealed class SettingsCalligraphy{
    object None : SettingsCalligraphy()
    data class Selected(val cal : Calligraphy) : SettingsCalligraphy()
}

