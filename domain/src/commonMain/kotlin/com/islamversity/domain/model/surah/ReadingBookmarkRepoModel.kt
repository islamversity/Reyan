package com.islamversity.domain.model.surah

import kotlinx.serialization.Serializable

@Serializable
data class ReadingBookmarkRepoModel(
    val pageType: PageType,

    val juz: Long? = null,

    val surahName: String,
    val surahId: String,

    val ayaId: String,
    val ayaOrder: Long,
) {

    @Serializable
    enum class PageType(val raw: String) {
        SURAH("SURAH"),
        JUZ("JUZ"),

        ;
    }
}
