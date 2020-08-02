package com.islamversity.db.model

import com.islamversity.db.No_rowId_name

data class Sora(
    val index : Long,
    val id : SoraId,
    val order : Long,
    val name : String
)

data class SoraWithFullName(
    val id : SoraId,
    val order : Long,
    val name : No_rowId_name
)