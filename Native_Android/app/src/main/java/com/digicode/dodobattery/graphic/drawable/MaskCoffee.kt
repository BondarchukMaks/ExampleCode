package com.digicode.dodobattery.graphic.drawable
import com.digicode.dodobattery.graphic.helpers.BaseHelper

class MaskCoffee(widgetName: String, batteryLevel: Int, templateData: BaseHelper.TemplateData,
                 lockData: BaseHelper.LockData, batteryData: BaseHelper.BatteryData
) : MaskADrawable(widgetName, batteryLevel,
    templateData, lockData, batteryData
){

    init {
        interSymbolSpacePercent = -10;
        topModifier = 0.47f;
    }

    override fun calculateBatteryLeftMarging(): Float {

        val numbersWidth = batteryData.numbers
            ?.sumBy { it.width }
            ?: 0

        val value = (canvasWidth.toFloat() / 2) - (numbersWidth / 2) - calculateX(canvasWidth.toFloat(), 15f)
        return value;
    }

}