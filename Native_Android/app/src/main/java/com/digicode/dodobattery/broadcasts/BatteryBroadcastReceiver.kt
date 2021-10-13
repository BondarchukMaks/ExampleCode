package com.digicode.dodobattery.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.digicode.dodobattery.provider.WidgetRemoteViewsFactory

class BatteryBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        WidgetRemoteViewsFactory.updateWidget(context)
    }
}