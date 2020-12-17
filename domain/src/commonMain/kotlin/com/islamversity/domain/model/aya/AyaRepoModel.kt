package com.islamversity.domain.model.aya

import com.islamversity.domain.model.surah.SurahID


data class AyaRepoModel(
    val id : AyaID,
    val content : String,
    val translation1 : String? = null,
    val translation2: String? = null,
    val order : Long,
    val juz: Long,
    val hizb : Long,
    val sajdahType : SajdahTypeRepoModel,
    val start : StartPartition?,
    val surahId: SurahID
)

enum class StartPartition{
    HIZB,
    JUZ,

}