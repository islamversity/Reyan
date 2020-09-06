package com.islamversity.domain.model.surah

import com.islamversity.db.model.SurahId

class SurahID(val id: String)

fun SurahID.toEntity() : SurahId =
    SurahId(id)
