package com.islamversity.db.model

import com.squareup.sqldelight.ColumnAdapter

inline class LanguageCode(val lang: String) {

    val isValid: Boolean
        get() =
            lang.length == 2 &&
                    lang == lang.toLowerCase()


    fun validated() {
        if (!isValid) {
            throw IllegalArgumentException("the language code provided is not valid=$lang")
        }
    }

}

inline class CalligraphyName(val name: String) {

    val isValid: Boolean
        get() = name.isNotBlank()

    fun validated() {
        if (!isValid) {
            throw IllegalArgumentException("the CalligraphyCode provided is not valid=$name")
        }
    }

}

//please don't change the order of parameters
data class Calligraphy(
    //en, fa, ar, ...
    internal val languageCode: LanguageCode,
    //uthmani, nastaligh
    internal val calligraphyName: CalligraphyName?
) {
    init {
        languageCode.validated()
        calligraphyName?.validated()
    }

    val code: String
        get() = if(calligraphyName != null){
            "${languageCode.lang}_${calligraphyName.name}"
        }else{
            languageCode.lang
        }


    companion object {
        /**
         * Follow the order of the primary constructor
         */
        operator fun invoke(code: String): Calligraphy {
            val codes = code.split("_")
            if (codes.isEmpty()) {
                throw IllegalArgumentException("a calligraphy is created using at least one parameter: LanguageCode")
            }
            val lang = LanguageCode(codes[0])
            val calligraphyName =  if(codes.size > 1){
                CalligraphyName(codes[1])
            }else{
                null
            }

            return Calligraphy(lang, calligraphyName)
        }
    }
}

class CalligraphyAdapter : ColumnAdapter<Calligraphy, String> {
    override fun decode(databaseValue: String): Calligraphy =
        Calligraphy(databaseValue)

    override fun encode(value: Calligraphy): String =
        value.code
}

class LanguageCodeAdapter : ColumnAdapter<LanguageCode, String> {
    override fun decode(databaseValue: String): LanguageCode =
        LanguageCode(databaseValue)

    override fun encode(value: LanguageCode): String =
        value.lang
}

class CalligraphyNameAdapter : ColumnAdapter<CalligraphyName, String> {
    override fun decode(databaseValue: String): CalligraphyName =
        CalligraphyName(databaseValue)

    override fun encode(value: CalligraphyName): String =
        value.name
}