package com.islamversity.db.model

import com.squareup.sqldelight.ColumnAdapter


sealed class BismillahType(
    open val name : String
) {
    companion object {
        fun fromFLag(flag : BismillahTypeFlag, name : String) : BismillahType =
            when (flag) {
                BismillahTypeFlag.NONE -> None(name)
                BismillahTypeFlag.NEEDED -> Needed(name)
                BismillahTypeFlag.FIRST_AYA -> FirstAya(name)
            }
    }

    data class None (
        val nameInCalligraphy : String
    ): BismillahType(nameInCalligraphy)
    data class Needed (
        val nameInCalligraphy : String
    ): BismillahType(nameInCalligraphy)
    data class FirstAya (
        val nameInCalligraphy : String
    ): BismillahType(nameInCalligraphy)
}

enum class BismillahTypeFlag(val raw: String) {
    NONE("NONE"),
    NEEDED("NEEDED"),
    FIRST_AYA("FIRST_AYA"),
    ;

    companion object{
        operator fun invoke(raw : String) : BismillahTypeFlag =
            fromRaw(raw)

        private fun fromRaw(raw : String) : BismillahTypeFlag =
            values().find { it.raw == raw } ?:
                    throw IllegalArgumentException("type= $raw is not correct")
    }
}

class BismillahTypeFlagAdapter : ColumnAdapter<BismillahTypeFlag, String> {
    override fun decode(databaseValue: String): BismillahTypeFlag =
        BismillahTypeFlag(databaseValue)

    override fun encode(value: BismillahTypeFlag): String =
        value.raw
}
