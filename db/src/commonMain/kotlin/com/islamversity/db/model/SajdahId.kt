package com.islamversity.db.model

import com.squareup.sqldelight.ColumnAdapter

inline class SajdahId(override val id: String) : EntityUUID

class SajdaIdAdapter : ColumnAdapter<SajdahId, String> {
    override fun decode(databaseValue: String): SajdahId =
        SajdahId(databaseValue)

    override fun encode(value: SajdahId): String =
        value.id
}
