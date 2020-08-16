package com.islamversity.db.model

import com.squareup.sqldelight.ColumnAdapter

inline class NameId(override val id: String) : EntityUUID

class NameIdAdapter : ColumnAdapter<NameId, String> {
    override fun decode(databaseValue: String): NameId =
        NameId(databaseValue)

    override fun encode(value: NameId): String =
        value.id
}
