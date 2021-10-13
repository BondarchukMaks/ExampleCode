package com.digicode.dodobattery.graphic.drawable

import com.digicode.dodobattery.graphic.helpers.BaseHelper

class MaskAqarium(widgetName: String, batteryLevel: Int, templateData: BaseHelper.TemplateData,
                  lockData: BaseHelper.LockData, batteryData: BaseHelper.BatteryData
) : MaskADrawable(widgetName, batteryLevel,
    templateData, lockData, batteryData
) {


    init {
        topModifier = 0.67f;
    }
}