package com.islamversity.domain.model

inline class QuranReadFontSize(val size : Int){
    companion object {
        val TRANSLATION_MIN = QuranReadFontSize(10)
        val MAIN_AYA_MIN = QuranReadFontSize(20)
        val DEFAULT = QuranReadFontSize(20)
        val MAX = QuranReadFontSize(50)
    }
}