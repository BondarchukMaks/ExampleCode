package com.digicode.dodobattery.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.digicode.dodobattery.data.model.GotFreeWidgetStatus
import com.digicode.dodobattery.data.preferences.PreferencesFacade
import com.digicode.dodobattery.utils.Constants
import com.digicode.dodobattery.view.ui.activity.SingleActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.android.AndroidInjection
import java.io.IOException
import java.net.URL
import javax.inject.Inject


class FBMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var preferencesFacade: PreferencesFacade

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this);
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun handleIntent(intent: Intent?) {
        super.handleIntent(intent)

        if(intent?.action == Constants.RECEIVE_ACTION){
            preferencesFacade.isGotFreeWidget.setValue(GotFreeWidgetStatus.FRIEND)

            preferencesFacade.freeAvalaibleWidgets
            preferencesFacade.freeAvalaibleWidgets.setValue(preferencesFacade.freeAvalaibleWidgets.getValue() +1)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val notification = remoteMessage.notification
        val data = remoteMessage.data
        sendNotification(notification!!, data)
    }

    private fun sendNotification(
        notification: RemoteMessage.Notification,
        data: Map<String, String>
    ) {
        val icon = BitmapFactory.decodeResource(
            resources,
            com.digicode.dodobattery.R.mipmap.ic_launcher
        )
        val intent = Intent(this, SingleActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationBuilder = NotificationCompat.Builder(this, "notification_channel_id")
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent)
            .setContentInfo(notification.title)
            .setLargeIcon(icon)
            .setColor(Color.RED)
            .setLights(Color.RED, 1000, 300)
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setSmallIcon(com.digicode.dodobattery.R.mipmap.ic_launcher)
        try {
            val picture_url = data["picture_url"]
            if (picture_url != null && "" != picture_url) {
                val url = URL(picture_url)
                val bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                notificationBuilder.setStyle(
                    NotificationCompat.BigPictureStyle().bigPicture(bigPicture)
                        .setSummaryText(notification.body)
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "notification_channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "channel description"
            channel.setShowBadge(true)
            channel.canShowBadge()
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}
