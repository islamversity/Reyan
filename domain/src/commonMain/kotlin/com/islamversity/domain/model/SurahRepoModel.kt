package com.islamversity.domain.model

data class SurahRepoModel(

    val index: Long,
    val id: SurahId,
    val order: Long,
    val name: String,
    val revealedType: RevealType,
    val bismillahType: BismillahTypeFlag,
    val sujdatype: Any = Unit

)