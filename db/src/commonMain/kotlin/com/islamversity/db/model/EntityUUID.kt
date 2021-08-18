package com.islamversity.db.model

import com.squareup.sqldelight.ColumnAdapter

interface EntityUUID {
    val id: String

    val isValid: Boolean
        get() = id.isNotBlank()

    val raw: RawId
        get() = RawId(id)
}

inline class RawId(override val id: String) : EntityUUID

class RawIdAdapter : ColumnAdapter<RawId, String> {
    override fun decode(databaseValue: String): RawId =
            RawId(databaseValue)

    override fun encode(value: RawId): String =
            value.id
}
