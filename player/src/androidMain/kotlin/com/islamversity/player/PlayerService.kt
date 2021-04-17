package com.islamversity.player

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes.Builder
import com.google.android.exoplayer2.util.Util

class PlayerService : Service() {

    lateinit var player: SimpleExoPlayer
    lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        //create the player engine
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        initializePlayer()

        showNotification()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action?.let {
            handleAction(it)
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()

//      release teh player engine
        releasePlayer()
        stopForeground(true)
    }

    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(this)
            //we can control if the user want's to stop if the headphone is disconnected
//            .setHandleAudioBecomingNoisy()
            .setWakeMode(C.WAKE_MODE_LOCAL)
            .setAudioAttributes(
                Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .build(),
                true
            )
            .build()
    }

    private fun releasePlayer() {
        player.stop()
        player.release()
    }

    private fun handleAction(action: String) {
        when (PlayerAction(action)) {
            PlayerAction.Play -> {
                //log
            }
            PlayerAction.Pause -> {

            }
            PlayerAction.Stop -> {

            }
            PlayerAction.NextAya -> {

            }
            PlayerAction.PreviousAya -> {

            }
        }
    }

    private fun play() {

    }

    private fun pause() {

    }

    private fun next() {

    }

    private fun previous() {

    }

    private fun showNotification() {
        createNotificationChannel()

        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID_PLAYER)

        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("سوره فاتحه آیه ۱")
            .setContentText("قاری: عبدل باسط")
            .setOngoing(true)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2, 3)
            )
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setWhen(0)

        if (player.playWhenReady) {
            val pendingIntentPause = PendingIntent.getService(
                this,
                PlayerAction.Pause.requestCode,
                createPlayerIntent(this, PlayerAction.Pause.action),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            notificationBuilder.addAction(
                NotificationCompat.Action.Builder(
                    R.drawable.ic_pause_black_24dp,
                    resources.getString(R.string.player_notification_pause),
                    pendingIntentPause
                ).build()
            )
        } else {
            val pendingIntentPlay = PendingIntent.getService(
                this,
                PlayerAction.Play.requestCode,
                createPlayerIntent(this, PlayerAction.Play.action),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            notificationBuilder.addAction(
                NotificationCompat.Action.Builder(
                    R.drawable.ic_play_black_24dp,
                    resources.getString(R.string.player_notification_play),
                    pendingIntentPlay
                ).build()
            )
        }

        val pendingIntentPrev = PendingIntent.getService(
            this,
            PlayerAction.PreviousAya.requestCode,
            createPlayerIntent(this, PlayerAction.PreviousAya.action),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        notificationBuilder.addAction(
            NotificationCompat.Action.Builder(
                R.drawable.ic_rewind_black_24dp,
                resources.getString(R.string.player_notification_previous_aya),
                pendingIntentPrev
            ).build()
        )

        val pendingIntentNextAya = PendingIntent.getService(
            this,
            PlayerAction.NextAya.requestCode,
            createPlayerIntent(this, PlayerAction.NextAya.action),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        notificationBuilder.addAction(
            NotificationCompat.Action.Builder(
                R.drawable.ic_fast_forward_black_24dp,
                resources.getString(R.string.player_notification_next_aya),
                pendingIntentNextAya
            ).build()
        )

        val pendingIntentStop = PendingIntent.getService(
            this,
            PlayerAction.Stop.requestCode,
            createPlayerIntent(this, PlayerAction.Stop.action),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        notificationBuilder.addAction(
            NotificationCompat.Action.Builder(
                R.drawable.ic_stop_circle_black_24dp,
                resources.getString(R.string.player_notification_stop),
                pendingIntentStop
            ).build()
        )

        startForeground(
            NOTIFICATION_ID_FOREGROUND_SERVICE,
            notificationBuilder.build()
        )
    }

    private fun createNotificationChannel() {
        if (Util.SDK_INT >= 26) {
            val channel = NotificationChannel(
                CHANNEL_ID_PLAYER,
                getString(R.string.player_notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.description = getString(R.string.player_notification_channel_description)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createPlayerIntent(context: Context, action: String) =
        Intent(context, PlayerService::class.java).apply {
            this.action = action
        }
}

enum class PlayerAction(
    val action: String,
    val requestCode : Int
) {

    Play("player_action_play", 1000097),
    Pause("player_action_pause", 1000096),
    Stop("player_action_stop", 1000099),
    NextAya("player_action_next_aya", 1000098),
    PreviousAya("player_action_previous_aya", 1000095),
    ;

    companion object {
        operator fun invoke(action: String) =
            values().find { it.action == action } ?: error("action= $action is not supported")
    }
}

private const val CHANNEL_ID_PLAYER = "channel_id_player"
private const val NOTIFICATION_ID_FOREGROUND_SERVICE = 100001
