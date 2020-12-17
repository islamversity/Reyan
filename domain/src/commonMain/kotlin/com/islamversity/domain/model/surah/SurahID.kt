package com.islamversity.domain.model.surah

import com.islamversity.db.model.SurahId

data class SurahID(val id: String)

fun SurahID.toEntity() : SurahId =
    SurahId(id)

fun SurahId.toRepo() : SurahID =
    SurahID(id)
