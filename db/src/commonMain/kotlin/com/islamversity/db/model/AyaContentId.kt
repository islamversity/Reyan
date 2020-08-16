package com.islamversity.db.model

import com.squareup.sqldelight.ColumnAdapter

inline class AyaContentId(override val id: String) : EntityUUID

class AyaContentIdAdapter : ColumnAdapter<AyaContentId, String> {
    override fun decode(databaseValue: String): AyaContentId =
        AyaContentId(databaseValue)

    override fun encode(value: AyaContentId): String =
        value.id
}
