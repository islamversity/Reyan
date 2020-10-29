package com.islamversity.db.model

import com.islamversity.db.No_rowId_name

data class SurahWithTwoName(
    val index : Long,
    val id : SurahId,
    val order : SurahOrderId,
    val arabicName : String,
    val mainName : String,
    val revealedType : RevealTypeFlag,
    val bismillahType: BismillahTypeFlag?,
    val ayaCount : Long
)


data class Surah(
    val index : Long,
    val id : SurahId,
    val order : SurahOrderId,
    val name : String,
    val revealedType : RevealTypeFlag,
    val bismillahType: BismillahTypeFlag?
)

data class SurahWithFullName(
    val id : SurahId,
    val order : SurahOrderId,
    val name : No_rowId_name,
    val revealTypeFlag : RevealTypeFlag,
    val bismillahTypeFlag: BismillahTypeFlag
)