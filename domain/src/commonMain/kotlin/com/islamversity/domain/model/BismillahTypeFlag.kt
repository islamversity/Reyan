package com.islamversity.domain.model;

enum class BismillahTypeFlag(val raw: String) {
    NONE("NONE"),
    NEEDED("NEEDED"),
    FIRST_AYA("FIRST_AYA"),
    ;

    companion object {
        operator fun invoke(raw: String): BismillahTypeFlag =
            fromRaw(raw)

        private fun fromRaw(raw: String): BismillahTypeFlag =
            values().find { it.raw == raw }
                ?: throw IllegalArgumentException("type= $raw is not correct")
    }
}

fun com.islamversity.db.model.BismillahTypeFlag.toDomain() = BismillahTypeFlag(raw)