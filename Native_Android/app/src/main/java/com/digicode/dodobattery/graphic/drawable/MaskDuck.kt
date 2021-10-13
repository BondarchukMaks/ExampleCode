package com.digicode.dodobattery.graphic.drawable

import android.graphics.Bitmap
import android.graphics.Canvas
import com.digicode.dodobattery.graphic.helpers.BaseHelper

class MaskDuck(batteryLevel: Int,
               templateData: BaseHelper.TemplateData, lockData: BaseHelper.LockData,
               batteryData: BaseHelper.BatteryData
) : MaskCDrawable(batteryLevel, templateData, lockData, batteryData)
{

    init {
        topModifier = 0.87f;

        val newList = mutableListOf<Bitmap>()

        batteryData.numbers?.forEach { bitmap ->
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, (bitmap.width * 0.8).toInt(),
                (bitmap.height * 0.8).toInt(),false);
            newList.add(scaledBitmap)
        }
        super.batteryData.numbers = newList.toList()

        super.batteryData.percents = Bitmap.createScaledBitmap(super.batteryData.percents, (super.batteryData.percents.width * 0.8).toInt(),
            (super.batteryData.percents.height * 0.8).toInt(),false);
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