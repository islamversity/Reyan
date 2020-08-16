package com.islamversity.db.model

import com.squareup.sqldelight.ColumnAdapter

inline class SurahOrderId(override val order: Long) : OrderId

class SurahOrderIdAdapter : ColumnAdapter<SurahOrderId, Long> {
    override fun decode(databaseValue: Long): SurahOrderId =
        SurahOrderId(databaseValue)

    override fun encode(value: SurahOrderId): Long =
        value.order
}