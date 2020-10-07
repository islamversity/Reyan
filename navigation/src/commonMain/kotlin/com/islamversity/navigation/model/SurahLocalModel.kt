package com.islamversity.navigation.model

import com.islamversity.navigation.SinkSerializer
import okio.BufferedSink
import okio.BufferedSource

data class SurahLocalModel(
    val surahID: String,
    val surahName : String,
    val startingAyaOrder : Long,
){
    companion object Sinker : SinkSerializer<SurahLocalModel> {
        const val EXTRA_SURAH_DETAIL = "extra_surah_detail"

        override fun BufferedSource.readFromSink(): SurahLocalModel =
            SurahLocalModel(
                readUtf8(),
                readUtf8(),
                readLong(),
            )

        override fun BufferedSink.writeToSink(obj: SurahLocalModel) {
            writeUtf8(obj.surahID)
            writeUtf8(obj.surahName)
            writeLong(obj.startingAyaOrder)
        }
    }
}
