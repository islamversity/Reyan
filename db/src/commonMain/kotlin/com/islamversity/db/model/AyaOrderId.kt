package com.islamversity.db.model

import com.squareup.sqldelight.ColumnAdapter

inline class AyaOrderId(override val order: Long) : OrderId

class AyaOrderIdAdapter : ColumnAdapter<AyaOrderId, Long> {
    override fun decode(databaseValue: Long): AyaOrderId =
        AyaOrderId(databaseValue)

    override fun encode(value: AyaOrderId): Long =
        value.order
}