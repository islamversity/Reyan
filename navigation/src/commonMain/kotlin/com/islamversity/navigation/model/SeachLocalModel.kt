package com.islamversity.navigation.model

import com.islamversity.navigation.jsonParser
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString

@Serializable
data class SearchLocalModel(
    val backTransitionName : String = "",
    val textTransitionName : String = ""
){
    companion object{
        const val EXTRA_SEARCH = "extra_search"
    }
}

fun SearchLocalModel.Companion.fromData(data: String): SearchLocalModel =
    jsonParser.decodeFromString(data)
