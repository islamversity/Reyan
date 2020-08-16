package com.islamversity.db.model

import com.squareup.sqldelight.ColumnAdapter

inline class RevealTypeId(override val id: String) : EntityUUID

class SurahRevealTypeIdAdapter : ColumnAdapter<RevealTypeId, String> {
    override fun decode(databaseValue: String): RevealTypeId =
        RevealTypeId(databaseValue)

    override fun encode(value: RevealTypeId): String =
        value.id
}
