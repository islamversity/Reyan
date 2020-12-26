package com.islamversity.reyan.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.islamversity.core.Logger
import com.islamversity.core.Severity
import com.islamversity.reyan.MainActivity
import com.islamversity.reyan.R
import com.islamversity.reyan.service.NotificationDataType.Companion.NOTIFICATION_DATA_KEY
import kotlin.random.Random


class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val LOGTAG = "FirebaseMessaging"

    override fun onNewToken(token: String) {
        Logger.log("Notification : Refreshed token: $token", Severity.Debug, LOGTAG, null)
        sendFCMTokenToServer(token)
    }

    private fun sendFCMTokenToServer(token: String) {
        // Cache.set(CacheKeys.Firebase.FCM_TOKEN_FIREBASE, token)
        Logger.log("Notification : sendRegistrationTokenToServer($token)", Severity.Debug, LOGTAG, null,)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Logger.log("Notification : From: ${remoteMessage.from}", Severity.Debug, LOGTAG, null,)

        val data = remoteMessage.data

        // Check if message contains a data payload.
        if (data.isNotEmpty()) {
            Logger.log("Notification : Message data payload: $data", Severity.Debug, LOGTAG, null,)

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
            Logger.log("Notification : 2-Message Notification Body: ${notification.body}", Severity.Debug, LOGTAG, null,)
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

        notificationManager.notify(Random.nextInt() /* ID of notification */, notificationBuilder.build())
    }

}