package com.islamversity.navigation.model

import com.islamversity.navigation.jsonParser
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString

@Serializable
data class SurahLocalModel(
    val surahID: String,
    val surahName : String,
    val startingAyaOrder : Long,
){
    companion object{
        const val EXTRA_SURAH_DETAIL = "extra_surah_detail"
    }
}

@Serializable
enum class BismillahType{
    NEEDED,
    FIRST_AYA,
    NONE,
    ;

    companion object{
        fun fromName(name : String) : BismillahType =
            values().find { it.name == name } ?: error("typeName= $name is not supported")
    }
}



fun SurahLocalModel.Companion.fromData(data: String): SurahLocalModel =
    jsonParser.decodeFromString(data)
