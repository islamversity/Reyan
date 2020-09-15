package com.islamversity.domain.model.surah

data class SurahRepoModel(
    val id: SurahID,
    val order: Int,
    var ayaCount : Long,
    val name: String,
    val revealedType: RevealedType,
    val bismillahType: BismillahRepoType,

)