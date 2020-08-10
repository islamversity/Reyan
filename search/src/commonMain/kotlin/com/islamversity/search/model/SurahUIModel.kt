package com.islamversity.search.model

import com.islamversity.domain.model.surah.SurahID

data class SurahUIModel (
    val id : SurahID,
    val order : Int,
    val name : String,
    val revealedTypeText : String
)