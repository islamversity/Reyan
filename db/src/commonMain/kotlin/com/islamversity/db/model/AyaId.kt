package com.islamversity.db.model

import com.squareup.sqldelight.ColumnAdapter

inline class AyaId(override val id: String) : EntityUUID

class AyaIdAdapter : ColumnAdapter<AyaId, String> {
    override fun decode(databaseValue: String): AyaId =
        AyaId(databaseValue)

    override fun encode(value: AyaId): String =
        value.id
}
