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

inline class AyaId(override val id: String) : EntityUUID

inline class AyaContentId(override val id: String) : EntityUUID

inline class CalligraphyId(override val id: String) : EntityUUID

inline class NameId(override val id: String) : EntityUUID

inline class SoraId(override val id: String) : EntityUUID

class RawIdAdapter : ColumnAdapter<RawId, String> {
    override fun decode(databaseValue: String): RawId =
        RawId(databaseValue)

    override fun encode(value: RawId): String =
        value.id
}

class AyaIdAdapter : ColumnAdapter<AyaId, String> {
    override fun decode(databaseValue: String): AyaId =
        AyaId(databaseValue)

    override fun encode(value: AyaId): String =
        value.id
}

class AyaContentIdAdapter : ColumnAdapter<AyaContentId, String> {
    override fun decode(databaseValue: String): AyaContentId =
        AyaContentId(databaseValue)

    override fun encode(value: AyaContentId): String =
        value.id
}

class CalligraphyIdAdapter : ColumnAdapter<CalligraphyId, String> {
    override fun decode(databaseValue: String): CalligraphyId =
        CalligraphyId(databaseValue)

    override fun encode(value: CalligraphyId): String =
        value.id
}

class NameIdAdapter : ColumnAdapter<NameId, String> {
    override fun decode(databaseValue: String): NameId =
        NameId(databaseValue)

    override fun encode(value: NameId): String =
        value.id
}

class SoraIdAdapter : ColumnAdapter<SoraId, String> {
    override fun decode(databaseValue: String): SoraId =
        SoraId(databaseValue)

    override fun encode(value: SoraId): String =
        value.id
}