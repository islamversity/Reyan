package com.islamversity.db.model

import com.islamversity.db.No_rowId_name

data class Surah(
    val index : Long,
    val id : SurahId,
    val order : SurahOrderId,
    val name : String,
    val revealedType : RevealType,
    val bismillahType: BismillahTypeFlag
)

data class SurahWithFullName(
    val id : SurahId,
    val order : SurahOrderId,
    val name : No_rowId_name,
    val revealTypeId: RevealTypeId,
    val revealTypeFlag : RevealTypeFlag,
    val bismillahId: BismillahId,
    val bismillahTypeFlag: BismillahTypeFlag
)