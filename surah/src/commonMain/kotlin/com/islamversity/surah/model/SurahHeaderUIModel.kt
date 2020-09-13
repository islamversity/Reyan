package com.islamversity.surah.model

data class SurahHeaderUIModel(
    val id: String,
    val number: String,
    val name: String,
    val nameTranslate: String,
    val origin : String,
    val verses: Int,
    val fontSize: Int,
    val showBismillah : Boolean
)
