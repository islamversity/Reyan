package com.islamversity.reyan.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.islamversity.domain.bus.FlowBus
import timber.log.Timber


class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Timber.d("Notification : Refreshed token: $token")
        sendFCMTokenToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.d("Notification : From: ${remoteMessage.from}")

        val dsf  = remoteMessage.data
        // Check if message contains a data payload.
        remoteMessage.data.isNotEmpty().let {
            Timber.d("Notification : Message data payload: ${remoteMessage.data}")
            FlowBus.publish(remoteMessage)
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Timber.d("Notification : 2-Message Notification Body: ${it.body}")
        }
    }

    private fun sendFCMTokenToServer(token: String) {
        // Cache.set(CacheKeys.Firebase.FCM_TOKEN_FIREBASE, token)
        Timber.d("Notification : sendRegistrationTokenToServer($token)")
    }














    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private fun sendNotification(messageBody: String) {

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.app_logo)
            .setContentTitle(getString(R.string.fcm_message))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

}