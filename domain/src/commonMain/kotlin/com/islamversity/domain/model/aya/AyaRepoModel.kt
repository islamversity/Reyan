package com.islamversity.domain.model.aya

data class AyaRepoModel(
    val id : AyaID,
    val content : String,
    val order : Long,
    val juz: Long,
    val hizb : Long,
)