package com.islamversity.quran_home.model

import com.islamversity.domain.model.BismillahTypeFlag
import com.islamversity.domain.model.RevealType

data class SurahUIModel(

    val index: Long,
    val id: String,
    val order: Long,
    val name: String,
    val revealedType: RevealType,
    val bismillahType: BismillahTypeFlag

)