package com.digicode.dodobattery.view.ui.activity

import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import androidx.annotation.RequiresApi
import com.digicode.dodobattery.Purchaser
import com.digicode.dodobattery.R
import com.digicode.dodobattery.broadcasts.BatteryBroadcastReceiver
import com.digicode.dodobattery.data.repository.AppRepository
import com.digicode.dodobattery.service.WidgetMonitorService
import com.digicode.dodobattery.utils.Constants
import com.digicode.dodobattery.view.ui.base.activity.BaseViewModelActivity
import com.digicode.dodobattery.viewModel.singleActivity.SingleActivityViewModel
import javax.inject.Inject

open class SingleActivity : BaseViewModelActivity<SingleActivityViewModel>()  {

    override fun getLayoutRes() = R.layout.activity_single

    override fun getViewModelKey() = SingleActivityViewModel::class

    @Inject
    lateinit var purchaser : Purchaser

    @Inject
    lateinit var appRepository: AppRepository

    private lateinit var monitorService: Intent

    override fun initView() {
        purchaser.initialize(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(BatteryBroadcastReceiver(), IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        appRepository.checkIfInstallationFromDeepLink(intent)

        val updateIntent = Intent(this, WidgetMonitorService::class.java)
        monitorService = updateIntent;


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            batteryOptimizationPermission()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startForegroundService(updateIntent)
        else
            startService(updateIntent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun batteryOptimizationPermission(){

        val deviceName = Build.MANUFACTURER;

        if(deviceName.contains(Constants.SAMSUNG_NAME))
            return

        val intent = Intent()

        val powerManager = getSystemService(POWER_SERVICE) as PowerManager

        if(!powerManager.isIgnoringBatteryOptimizations(packageName)){
            intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!purchaser.billingProcessor.handleActivityResult(requestCode, resultCode, data)){
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        purchaser.release()
        stopService(monitorService);
    }

    override fun subscribeOnViewModelEvents() {
    }
}
