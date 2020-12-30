package com.islamversity.navigation.model

import com.islamversity.navigation.jsonParser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString

@Serializable
sealed class SurahLocalModel {

    @Serializable
    @SerialName("FullSurah")
    data class FullSurah(
        val surahName: String,
        val surahID: String,
        val startingAyaOrder: Long,
    ) : SurahLocalModel()

    @Serializable
    @SerialName("FullJuz")
    data class FullJuz(
        val juzOrder: Long
    ) : SurahLocalModel()

    companion object {
        const val EXTRA_SURAH_DETAIL = "extra_surah_detail"
    }
}

fun SurahLocalModel.Companion.fromData(data: String): SurahLocalModel =
    jsonParser.decodeFromString(SurahLocalModel.serializer(), data)
