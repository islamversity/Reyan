package com.islamversity.domain.model.surah

data class SurahRepoModel (
    val id : SurahID,
    val order : Int,
    val name : String,
    val revealedType : RevealedType
)