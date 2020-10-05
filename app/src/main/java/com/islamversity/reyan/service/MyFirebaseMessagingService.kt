package com.islamversity.reyan.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.islamversity.reyan.MainActivity
import com.islamversity.reyan.R
import com.islamversity.reyan.service.NotificationDataType.Companion.NOTIFICATION_DATA_KEY
import timber.log.Timber


class MyFirebaseMessagingService: FirebaseMessagingService() {


    override fun onNewToken(token: String) {
        Timber.d("Notification : Refreshed token: $token")
        sendFCMTokenToServer(token)
    }

    private fun sendFCMTokenToServer(token: String) {
        // Cache.set(CacheKeys.Firebase.FCM_TOKEN_FIREBASE, token)
        Timber.d("Notification : sendRegistrationTokenToServer($token)")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.d("Notification : From: ${remoteMessage.from}")

        val data  = remoteMessage.data

        // Check if message contains a data payload.
        if (data.isNotEmpty()) {
            Timber.d("Notification : Message data payload: $data")

            val bundle = Bundle()
            for ((key, value) in data.entries) {
                bundle.putString(key, value)
            }

            showNotification(
                data["title"],
                data["body"],
                bundle
            )
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let { notification ->
            Timber.d("Notification : 2-Message Notification Body: ${notification.body}")
        }
    }

    private fun showNotification(title: String?, body: String?, dataBundle: Bundle) {

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        intent.putExtra(NOTIFICATION_DATA_KEY, dataBundle)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

}