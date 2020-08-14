package com.islamversity.navigation.model

import com.islamversity.navigation.SinkSerializer
import okio.BufferedSink
import okio.BufferedSource


data class SoraDetailLocalModel(
    val backTransitionName : String = "",
    val textTransitionName : String = "",
    val soraID: String,
    val soraName : String,
    val ayaNumber : Int
){
    companion object Sinker : SinkSerializer<SoraDetailLocalModel> {
        const val EXTRA_SORA_DETAIL = "extra_sora_detail"

        override fun BufferedSource.readFromSink(): SoraDetailLocalModel =
            SoraDetailLocalModel(
                readUtf8(),
                readUtf8(),
                readUtf8(),
                readUtf8(),
                readInt()
            )

        override fun BufferedSink.writeToSink(obj: SoraDetailLocalModel) {
            writeUtf8(obj.backTransitionName)
            writeUtf8(obj.textTransitionName)
            writeUtf8(obj.soraID)
            writeUtf8(obj.soraName)
            writeInt(obj.ayaNumber)

        }
    }
}
