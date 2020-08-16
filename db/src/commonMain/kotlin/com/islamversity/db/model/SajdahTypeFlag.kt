package com.islamversity.db.model

import com.squareup.sqldelight.ColumnAdapter

enum class SajdahTypeFlag(val raw: String) {
    NONE("NONE"),
    RECOMMENDED("RECOMMENDED"),
    OBLIGATORY("OBLIGATORY"),
    ;

    companion object{
        operator fun invoke(raw : String) : SajdahTypeFlag =
            fromRaw(raw)

        private fun fromRaw(raw : String) : SajdahTypeFlag =
            values().find { it.raw == raw } ?:
                    throw IllegalArgumentException("type= $raw is not correct")
    }
}

class SajdahTypeFlagAdapter : ColumnAdapter<SajdahTypeFlag, String> {
    override fun decode(databaseValue: String): SajdahTypeFlag =
        SajdahTypeFlag(databaseValue)

    override fun encode(value: SajdahTypeFlag): String =
        value.raw
}
