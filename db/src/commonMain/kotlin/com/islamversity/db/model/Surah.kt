package com.islamversity.db.model

import com.islamversity.db.No_rowId_name

data class Surah(
    val index : Long,
    val id : SurahId,
    val order : SurahOrderId,
    val name : String,
    val revealedType : RevealType
)

data class SurahWithFullName(
    val id : SurahId,
    val order : SurahOrderId,
    val name : No_rowId_name,
    val revealTypeId: RevealTypeId,
    val revealTypeFlag : RevealTypeFlag
)