package com.islamversity.navigation.model

import com.islamversity.navigation.SinkSerializer
import okio.BufferedSink
import okio.BufferedSource

data class SearchLocalModel(
    val backTransitionName : String = "",
    val textTransitionName : String = ""
){
    companion object Sinker : SinkSerializer<SearchLocalModel>{
        const val EXTRA_SEARCH = "extra_search"

        override fun BufferedSource.readFromSink(): SearchLocalModel =
            SearchLocalModel(
                readUtf8(),
                readUtf8()
            )

        override fun BufferedSink.writeToSink(obj: SearchLocalModel) {
            writeUtf8(obj.backTransitionName)
            writeUtf8(obj.textTransitionName)
        }
    }
}