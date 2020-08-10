package com.islamversity.navigation.model

import com.islamversity.navigation.SinkSerializer
import okio.BufferedSink
import okio.BufferedSource


data class SurahDetailLocalModel(
    val backTransitionName : String = "",
    val textTransitionName : String = "",
    val surahID: String,
    val surahName : String,
    val ayaOrder : Int
){
    companion object Sinker : SinkSerializer<SurahDetailLocalModel> {
        const val EXTRA_SURAH_DETAIL = "extra_surah_detail"

        override fun BufferedSource.readFromSink(): SurahDetailLocalModel =
            SurahDetailLocalModel(
                readUtf8(),
                readUtf8(),
                readUtf8(),
                readUtf8(),
                readInt()
            )

        override fun BufferedSink.writeToSink(obj: SurahDetailLocalModel) {
            writeUtf8(obj.backTransitionName)
            writeUtf8(obj.textTransitionName)
            writeUtf8(obj.surahID)
            writeUtf8(obj.surahName)
            writeInt(obj.ayaOrder)

        }
    }
}
