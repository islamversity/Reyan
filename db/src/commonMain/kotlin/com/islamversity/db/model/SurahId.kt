package com.islamversity.db.model

import com.squareup.sqldelight.ColumnAdapter

inline class SurahId(override val id: String) : EntityUUID

class SurahIdAdapter : ColumnAdapter<SurahId, String> {
    override fun decode(databaseValue: String): SurahId = SurahId(databaseValue)

    override fun encode(value: SurahId): String = value.id
}
