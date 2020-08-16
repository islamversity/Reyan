package com.islamversity.db.model

interface OrderId {
    val order: Long

    val isValid: Boolean
        get() = order > 0
}

