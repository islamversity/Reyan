package com.islamversity.domain.model.surah

sealed class RevealedType(
    open val name : String
) {
    data class MECCAN (
        val nameInCalligraphy : String
    ): RevealedType(nameInCalligraphy)
    data class MEDINAN (
        val nameInCalligraphy : String
    ): RevealedType(nameInCalligraphy)
}