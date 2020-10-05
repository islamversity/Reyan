package com.islamversity.reyan.service

enum class NotificationDataType {

    NONE, NEW_VERSION, FORCE_UPDATE;

    companion object {

        val notificationTypeMap = hashMapOf(
            "new_version" to NEW_VERSION,
            "force_update" to FORCE_UPDATE
        )


        val NOTIFICATION_DATA_KEY = "NOTIFICATION_DATA_KEY"
    }
}