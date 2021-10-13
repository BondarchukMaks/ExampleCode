package com.digicode.dodobattery.battery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import com.digicode.dodobattery.utils.extensions.isMoreThenNull

class BatteryReceiver(
    private val listener: ((Int, Boolean) -> Unit)? = null
): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val batteryLevel = getBatteryLevel(intent)
        val isCharging = getChargingStatus(intent)

        listener?.invoke(batteryLevel, isCharging)
    }

     fun getBatteryLevel(intent: Intent?): Int {
        val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        var batteryPct = -1
        if ( level.isMoreThenNull() && scale.isMoreThenNull()) {
            batteryPct = (level!! * 100) / scale!!
        }
        return batteryPct
    }

    private fun getChargingStatus(intent: Intent?): Boolean {
        val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        return status == BatteryManager.BATTERY_STATUS_CHARGING
    }

    fun getBatteryStatus(context: Context?,listener: ((Int, Boolean) -> Unit)) {

        val ifilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus: Intent? = context?.registerReceiver(null, ifilter)

        batteryStatus?.let {
            val level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val batteryPct = (level * 100 / scale.toFloat()).toInt()
            listener.invoke(batteryPct,status == BatteryManager.BATTERY_STATUS_CHARGING)
        }
    }

}