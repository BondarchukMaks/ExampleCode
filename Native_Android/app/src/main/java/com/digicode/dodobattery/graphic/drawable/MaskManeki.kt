package com.digicode.dodobattery.graphic.drawable

import android.graphics.Bitmap
import android.graphics.Canvas
import com.digicode.dodobattery.graphic.helpers.BaseHelper

class MaskManeki(batteryLevel: Int,
                 templateData: BaseHelper.TemplateData,
                 lockData: BaseHelper.LockData,
                 batteryData: BaseHelper.BatteryData
) : MaskCDrawable(batteryLevel, templateData, lockData, batteryData){


    init {
        topModifier = 0.80f;
    }
    override fun drawBatteryLevel(
        numbers: List<Bitmap>?,
        percents: Bitmap,
        canvas: Canvas,
        leftMarging: Float
    ) {
        val topPos = calculateY(canvas.height.toFloat(), 55f)
        drawBatteryLevel(numbers, percents, canvas, leftMarging, topPos)
    }
}