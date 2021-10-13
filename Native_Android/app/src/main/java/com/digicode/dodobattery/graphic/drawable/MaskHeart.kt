package com.digicode.dodobattery.graphic.drawable

import com.digicode.dodobattery.graphic.helpers.BaseHelper

class MaskHeart(batteryLevel: Int,
                templateData: BaseHelper.TemplateData, lockData: BaseHelper.LockData,
                batteryData: BaseHelper.BatteryData
) : MaskCDrawable(batteryLevel, templateData, lockData, batteryData){


    init {
        topModifier = 0.77f;
    }

}