package com.islamversity.quran_home.feature.surah.model

import com.islamversity.domain.model.surah.RevealedType

data class SurahUIModel(
    val id: String,
    val order: Int,
    val name: String,
    val revealedType: RevealedType

)