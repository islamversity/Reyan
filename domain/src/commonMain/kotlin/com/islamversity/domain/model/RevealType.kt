package com.islamversity.domain.model


sealed class RevealType(
    open val name: String
) {
    companion object {
        fun fromFLag(flag: RevealTypeFlag, name: String): RevealType =
            when (flag) {
                RevealTypeFlag.MECCAN -> MECCAN(name)
                RevealTypeFlag.MEDINAN -> MEDINAN(name)
            }
    }

    data class MECCAN(
        val nameInCalligraphy: String
    ) : RevealType(nameInCalligraphy)

    data class MEDINAN(
        val nameInCalligraphy: String
    ) : RevealType(nameInCalligraphy)
}

fun com.islamversity.db.model.RevealType.toDomain(): RevealType {
    return when (this) {
        is com.islamversity.db.model.RevealType.MECCAN -> RevealType.MECCAN(name)
        is com.islamversity.db.model.RevealType.MEDINAN -> RevealType.MEDINAN(name)
    }
}

enum class RevealTypeFlag(val raw: String) {
    MECCAN("MECCAN"),
    MEDINAN("MEDINAN"),
    ;
}