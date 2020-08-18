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
        val DEFAULT = Calligraphy
    }
}

fun Calligraphy.toDB() = com.islamversity.db.model.Calligraphy(code.code)

