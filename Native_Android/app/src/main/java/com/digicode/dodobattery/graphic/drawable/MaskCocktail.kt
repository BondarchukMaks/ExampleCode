package com.digicode.dodobattery.graphic.drawable

import com.digicode.dodobattery.graphic.helpers.BaseHelper

class MaskCocktail(batteryLevel: Int,
                   templateData: BaseHelper.TemplateData, lockData: BaseHelper.LockData,
                   batteryData: BaseHelper.BatteryData
) : MaskBDrawable(batteryLevel, templateData, lockData, batteryData){



    init {
        topModifier = 0.73f;
    }
}