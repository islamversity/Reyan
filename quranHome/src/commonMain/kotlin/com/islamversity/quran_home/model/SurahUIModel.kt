package com.islamversity.quran_home.model

import com.islamversity.domain.model.RevealType
import com.islamversity.domain.model.surah.RevealedType
import com.islamversity.domain.model.surah.SurahID

data class SurahUIModel(

    val id: SurahID,
    val order: Int,
    val name: String,
    val revealedType: RevealedType
)