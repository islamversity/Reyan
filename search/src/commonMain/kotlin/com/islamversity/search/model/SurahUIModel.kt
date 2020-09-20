package com.islamversity.search.model

import com.islamversity.domain.model.surah.RevealedType
import com.islamversity.domain.model.surah.SurahID

data class SurahUIModel(
    val id: SurahID,
    val order: Int,
    val arabicName: String,
    val mainName: String,
    val revealedType: RevealedType,
    val ayaCount: Long,
)
