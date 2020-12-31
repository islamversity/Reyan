package com.islamversity.navigation.model

import com.islamversity.navigation.jsonParser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class SurahLocalModel {

    @Serializable
    @SerialName("fullSurah")
    data class FullSurah(
        val surahName: String,
        val surahID: String,
        val startingAyaOrder: Long,
    ) : SurahLocalModel()

    @Serializable
    @SerialName("fullJuz")
    data class FullJuz(
        val juzOrder: Long
    ) : SurahLocalModel()

    companion object {
        const val EXTRA_SURAH_DETAIL = "extra_surah_detail"
    }
}

fun SurahLocalModel.Companion.fromData(data: String): SurahLocalModel =
    jsonParser.decodeFromString(SurahLocalModel.serializer(), data)
