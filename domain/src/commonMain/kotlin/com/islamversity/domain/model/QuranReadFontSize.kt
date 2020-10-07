package com.islamversity.domain.model

inline class QuranReadFontSize(val size : Int){
    companion object {
        val MINIMUM = QuranReadFontSize(20)
        val DEFAULT = QuranReadFontSize(40)
    }
}