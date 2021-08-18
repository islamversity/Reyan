package com.islamversity.db.model

import com.squareup.sqldelight.ColumnAdapter

enum class BismillahTypeFlag(val raw: String) {
    NEEDED("NEEDED"),
    FIRST_AYA("FIRST_AYA"),
    ;

    companion object {
        operator fun invoke(raw: String): BismillahTypeFlag? =
                fromRaw(raw)

        private fun fromRaw(raw: String): BismillahTypeFlag? =
                values().find { it.raw == raw }
    }
}

class BismillahTypeFlagAdapter : ColumnAdapter<BismillahTypeFlag, String> {
    override fun decode(databaseValue: String): BismillahTypeFlag =
            BismillahTypeFlag(databaseValue)!!

    override fun encode(value: BismillahTypeFlag): String =
            value.raw
}
