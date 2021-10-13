package com.digicode.dodobattery.graphic.helpers

import android.content.Context
import android.graphics.drawable.Drawable
import com.digicode.dodobattery.data.model.Widget
import com.digicode.dodobattery.graphic.drawable.ImageSeqDrawable
import com.digicode.dodobattery.graphic.drawable.MaskADrawable
import com.digicode.dodobattery.graphic.drawable.MaskCDrawable
import com.digicode.dodobattery.graphic.drawable.MaskDuck

class MaskDuckHelper : MaskCHelper() {

    override fun create(
        context: Context,
        widget: Widget,
        batteryIndicators: Pair<Int, Boolean>,
        showWatermark: Boolean
    ): Drawable {
        val templateBitmaps = createTemplate(context, widget.template)
        val lockBitmaps = getLockData(context, widget.lockedStatus, showWatermark)
        val batteryBitmaps = getBatteryData(
            context,
            batteryIndicators,
            widget.template.numbers,
            widget.template.percents)


        return MaskDuck(
            batteryIndicators.first,
            templateBitmaps,
            lockBitmaps,
            batteryBitmaps
        )
    }
}