package com.islamversity.db.model

import com.islamversity.db.No_rowId_aya_content

sealed class Aya(
    open val index: Long,
    open val id: AyaId,
    open val order: Long,
    open val content: String
) {
    data class WithSoraId(
        override val index: Long,
        override val id: AyaId,
        override val order: Long,
        override val content: String,
        val soraId: SoraId
    ) : Aya(index, id, order, content)

    data class WithSoraOrderCalligraphy(
        override val index: Long,
        override val id: AyaId,
        override val order: Long,
        override val content: String,
        val soraOrder: Long,
        val soraCalligraphy : Calligraphy
    ) : Aya(index, id, order, content)
}

data class AyaWithFullContent(
    val id: AyaId,
    val soraId: SoraId,
    val order: Long,
    val content: No_rowId_aya_content
)