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
        val startingFrom: StartingAya = StartingAya.ID.Beginning,
    ) : SurahLocalModel()

    @Serializable
    @SerialName("fullJuz")
    data class FullJuz(
        val juzOrder: Long,
        val startingFrom: StartingAya.ID = StartingAya.ID.Beginning,
    ) : SurahLocalModel()

    companion object {
        const val EXTRA_SURAH_DETAIL = "extra_surah_detail"
    }
}

@Serializable
sealed class StartingAya {

    @Serializable
    data class Order(val order : Long) : StartingAya()

    @Serializable
    sealed class ID : StartingAya(){
        @Serializable
        object Beginning : ID()

        @Serializable
        data class AyaId(val id : String) : ID()
    }
}

fun SurahLocalModel.Companion.fromData(data: String): SurahLocalModel =
    jsonParser.decodeFromString(SurahLocalModel.serializer(), data)
