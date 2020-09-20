package com.islamversity.domain.model.surah

data class SurahRepoModel(
    val id: SurahID,
    val order: Int,
    val arabicName: String,
    val mainName: String,
    val revealedType: RevealedType,
    val bismillahType: BismillahRepoType,
    val ayaCount : Long,
)
