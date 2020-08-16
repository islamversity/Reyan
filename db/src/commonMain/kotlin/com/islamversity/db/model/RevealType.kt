package com.islamversity.db.model

import com.squareup.sqldelight.ColumnAdapter

sealed class RevealType(
    open val name : String
) {
    companion object {
        fun fromFLag(flag : RevealTypeFlag, name : String) : RevealType =
            when (flag) {
                RevealTypeFlag.MECCAN -> MECCAN(name)
                RevealTypeFlag.MEDINAN -> MEDINAN(name)
            }
    }

    data class MECCAN (
        val nameInCalligraphy : String
    ): RevealType(nameInCalligraphy)
    data class MEDINAN (
        val nameInCalligraphy : String
    ): RevealType(nameInCalligraphy)
}

enum class RevealTypeFlag(val raw : String){
    MECCAN("MECCAN"),
    MEDINAN("MEDINAN"),
    ;
}


class RevealTypeFlagAdapter : ColumnAdapter<RevealTypeFlag, String> {
    override fun decode(databaseValue: String): RevealTypeFlag =
        RevealTypeFlag.values().find { it.raw == databaseValue } ?:
        throw IllegalArgumentException("value= $databaseValue is not a correct flag")

    override fun encode(value: RevealTypeFlag): String =
        value.raw
}
