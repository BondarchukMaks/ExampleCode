package com.digicode.dodobattery.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.digicode.dodobattery.R
import com.digicode.dodobattery.battery.BatteryReceiver
import com.digicode.dodobattery.provider.WidgetRemoteViewsFactory


class WidgetMonitorService(): Service() {

    private lateinit var batteryReceiver: BatteryReceiver

    private val NOTIFICATION_ID : Int = 11
    private val NOTIFICATION_CHANNEL_ID: String = "com.digicode.dodo"

    override fun onBind(p0: Intent?): IBinder? {
        return null;
    }

    override fun onCreate() {
        super.onCreate()

        batteryReceiver = BatteryReceiver { level, status ->
            updateWidget()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                updateNotification(level)
        }




        val batteryIntent = registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground(batteryIntent)
        else
            startForeground(NOTIFICATION_ID, Notification())

        updateWidget()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startMyOwnForeground(batteryIntent: Intent?) {

        createNotificationChannel()
        val notification = createNotification(batteryReceiver.getBatteryLevel(batteryIntent))
        startForeground(NOTIFICATION_ID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(){

        val channelName = "widgetMonitorChannel"
        val chan = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_NONE
        )

        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val manager = getNotificationManager()
        manager.createNotificationChannel(chan)
    }

    private fun getNotificationManager() : NotificationManager{
        return getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateNotification(batteryLevel: Int){
        val notification = createNotification(batteryLevel)
        val manager = getNotificationManager()
        manager.notify(NOTIFICATION_ID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotification(batteryLevel: Int) : Notification {

        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        val remoteViews = RemoteViews(
            getPackageName(),
            R.layout.layout_foreground_notification
        )

        builder.setContent(remoteViews)
        builder.priority = NotificationCompat.PRIORITY_LOW
        builder.setVisibility(NotificationCompat.VISIBILITY_PRIVATE)

        val iconID =  resources.getIdentifier("s$batteryLevel", "drawable", packageName)
        builder.setSmallIcon(iconID)
        return builder.build()
    }

    private fun updateWidget(){
        WidgetRemoteViewsFactory.updateWidget(this)
    }

    private fun restartSelf(){
        val updateIntent = Intent(this, WidgetMonitorService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startForegroundService(updateIntent)
        else
            startService(updateIntent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(batteryReceiver)

        restartSelf()
    }
}