package com.islamversity.db.model

import com.islamversity.db.No_rowId_aya_content
import com.squareup.sqldelight.ColumnAdapter

data class Aya(
    val index: Long,
    val id: AyaId,
    val order: AyaOrderId,
    val surahId: SurahId,
    val content: String,
    val translation1: String? = null,
    val translation2: String? = null,
    val sajdahType: SajdahTypeFlag?,
    val juz: Juz,
    val hizb: HizbQuarter,
    val startOfHizb: Boolean?,
    val endingOfHizb: Boolean?,
) {
    init {
        juz.validated()
        hizb.validated()
    }
}

data class AyaWithFullContent(
    val id: AyaId,
    val surahId: SurahId,
    val order: AyaOrderId,
    val content: No_rowId_aya_content,
    val sajdahTypeFlag: SajdahTypeFlag,
    val juz: Juz,
    val hizb: HizbQuarter,
    val startOfHizb: Boolean? = null,
    val endingOfHizb: Boolean? = null,
) {
    init {
        juz.validated()
        hizb.validated()
    }
}

inline class Juz(val value: Long) {
    private val isValid: Boolean
        get() = value in juzRange

    fun validated() {
        if (!isValid) {
            error("juz=$value is not a valid juz")
        }
    }

    companion object {
        private val juzRange = 1..30
    }
}

inline class HizbQuarter(val value: Long) {
    private val isValid: Boolean
        get() = value in hizbRange

    fun validated() {
        if (!isValid) {
            error("hizb=$value is not a valid hizb")
        }
    }

    companion object {
        private val hizbRange = 1..240
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
