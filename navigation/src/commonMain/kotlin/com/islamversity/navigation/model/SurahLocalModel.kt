package com.islamversity.navigation.model

import com.islamversity.navigation.jsonParser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class SurahLocalModel {

    @Serializable
    @SerialName("fullsurah")
    data class FullSurah(
        @SerialName("surahname")
        val surahName: String,
        @SerialName("surahid")
        val surahID: String,
        @SerialName("startingayaorder")
        val startingAyaOrder: Long,
    ) : SurahLocalModel()

    @Serializable
    @SerialName("fulljuz")
    data class FullJuz(
        @SerialName("juzorder")
        val juzOrder: Long
    ) : SurahLocalModel()

    companion object {
        const val EXTRA_SURAH_DETAIL = "extra_surah_detail"
    }
}

fun SurahLocalModel.Companion.fromData(data: String): SurahLocalModel =
    jsonParser.decodeFromString(SurahLocalModel.serializer(), data)
