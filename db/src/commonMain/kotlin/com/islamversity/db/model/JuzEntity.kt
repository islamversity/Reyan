package com.islamversity.db.model

data class JuzEntity(
    val startingAyaId : AyaId,
    val startingAyaOrder : AyaOrderId,
    val startingSurahId: SurahId,
    val startingSurahName : String,

    val endingAyaId : AyaId,
    val endingAyaOrder : AyaOrderId,
    val endingSurahId: SurahId,
    val endingSurahName : String,

    val juzOrderIndex : Juz,
    val hizbs : List<HizbEntity>,
)