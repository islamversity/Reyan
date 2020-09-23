package com.islamversity.domain.model

data class HizbRepoModel (
    val startingAyaId : String,
    val startingAyaOrder : Long,
    val startingSurahId: String,
    val startingSurahName : String,

    val endingAyaId : String,
    val endingAyaOrder : Long,
    val endingSurahId: String,
    val endingSurahName : String,

    val juz : Long,
    val hizb: Long,
)