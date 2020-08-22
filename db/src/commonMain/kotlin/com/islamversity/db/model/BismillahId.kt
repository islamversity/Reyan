package com.islamversity.db.model

import com.squareup.sqldelight.ColumnAdapter

inline class BismillahId(override val id: String) : EntityUUID

class BismillahIdAdapter : ColumnAdapter<BismillahId, String> {
    override fun decode(databaseValue: String): BismillahId =
        BismillahId(databaseValue)

    override fun encode(value: BismillahId): String =
        value.id
}
