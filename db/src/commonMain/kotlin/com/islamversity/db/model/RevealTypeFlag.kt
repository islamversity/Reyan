package com.islamversity.db.model

import com.squareup.sqldelight.ColumnAdapter

enum class RevealTypeFlag(val raw: String) {
    MECCAN("MECCAN"),
    MEDINAN("MEDINAN"),
    ;
}


class RevealTypeFlagAdapter : ColumnAdapter<RevealTypeFlag, String> {
    override fun decode(databaseValue: String): RevealTypeFlag =
            RevealTypeFlag.values().find { it.raw == databaseValue }
                    ?: throw IllegalArgumentException("value= $databaseValue is not a correct flag")

    override fun encode(value: RevealTypeFlag): String =
            value.raw
}
