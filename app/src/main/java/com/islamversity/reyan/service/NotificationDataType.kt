package com.islamversity.reyan.service

import java.lang.reflect.GenericArrayType

enum class NotificationDataType(val raw : String) {

    GENERIC("none"),
    NEW_VERSION("new_version"),
    FORCE_UPDATE("force_update");

    companion object {
        operator fun invoke(raw: String) :NotificationDataType =
            values().find { it.raw == raw } ?: GENERIC

        val NOTIFICATION_DATA_KEY = "NOTIFICATION_DATA_KEY"
    }
}