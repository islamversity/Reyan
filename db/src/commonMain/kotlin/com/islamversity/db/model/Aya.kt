package com.islamversity.db.model

import com.islamversity.db.No_rowId_aya_content
import com.squareup.sqldelight.ColumnAdapter

data class Aya(
    val index: Long,
    val id: AyaId,
    val order: AyaOrderId,
    val surahId: SurahId,
    val content: String,
    val sajdahType : SajdahTypeFlag,
    val juz: Juz,
    val hizb: HizbQuarter
){
    init{
        juz.validated()
        hizb.validated()
    }
}

data class AyaWithFullContent(
    val id: AyaId,
    val surahId: SurahId,
    val order: AyaOrderId,
    val content: No_rowId_aya_content,
    val sajdahId: SajdahId,
    val sajdahTypeFlag: SajdahTypeFlag,
    val juz: Juz,
    val hizb: HizbQuarter
){
    init {
        juz.validated()
        hizb.validated()
    }
}

private val juzRange = 1..30
private val hizbRange = 1..240

inline class Juz(val value: Long) {
    val isValid: Boolean
        get() = value in juzRange

    fun validated(){
        if(!isValid){
            error("juz=$value is not a valid juz")
        }
    }
}

inline class HizbQuarter(val value: Long){
    val isValid: Boolean
        get() = value in hizbRange

    fun validated(){
        if(!isValid){
            error("hizb=$value is not a valid hizb")
        }
    }
}

class JuzAdapter : ColumnAdapter<Juz, Long> {
    override fun decode(databaseValue: Long): Juz =
        Juz(databaseValue)

    override fun encode(value: Juz): Long =
        value.value
}

class HizbAdapter : ColumnAdapter<HizbQuarter, Long> {
    override fun decode(databaseValue: Long): HizbQuarter =
        HizbQuarter(databaseValue)

    override fun encode(value: HizbQuarter): Long =
        value.value
}
