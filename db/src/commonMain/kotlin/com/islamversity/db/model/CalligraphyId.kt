package com.islamversity.db.model

import com.squareup.sqldelight.ColumnAdapter

inline class CalligraphyId(override val id: String) : EntityUUID

class CalligraphyIdAdapter : ColumnAdapter<CalligraphyId, String> {
    override fun decode(databaseValue: String): CalligraphyId =
            CalligraphyId(databaseValue)

    override fun encode(value: CalligraphyId): String =
            value.id
}
