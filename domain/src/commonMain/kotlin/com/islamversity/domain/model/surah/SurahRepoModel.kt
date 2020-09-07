package com.islamversity.domain.model.surah

data class SurahRepoModel (
    val id : SurahID,
    val order : Int,
    var ayaCount : Int = 0,
    val name : String,
    val revealedType : RevealedType
)