package com.islamversity.quran_home.feature.surah.model

import com.islamversity.domain.model.surah.RevealedType
import com.islamversity.domain.model.surah.SurahID

data class SurahUIModel(
    val id: SurahID,
    val order: Int,
    val ayaCount : Long,
    val name: String,
    val revealedType: RevealedType
)