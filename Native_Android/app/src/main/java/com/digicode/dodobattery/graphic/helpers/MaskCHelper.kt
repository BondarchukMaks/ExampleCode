package com.digicode.dodobattery.graphic.helpers

import android.content.Context
import android.graphics.drawable.Drawable
import com.digicode.dodobattery.data.model.Template
import com.digicode.dodobattery.data.model.Widget
import com.digicode.dodobattery.graphic.drawable.MaskCDrawable
import com.digicode.dodobattery.graphic.drawable.MaskManeki

open class MaskCHelper: BaseHelper() {

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

        return MaskCDrawable(
            batteryIndicators.first,
            templateBitmaps,
            lockBitmaps,
            batteryBitmaps
        )
    }

    protected open fun createTemplate(context: Context,
                               template: Template): TemplateData {
        val mainFull = getSimpleBitmap(context, template.mainImage, 0)
        val mainEmpty = getSimpleBitmap(context, template.mainImage, 1)
        val level = getSimpleBitmap(context, template.mainImage, 2)

        val maskLevel = getSimpleBitmap(context, template.mask, 0)
        val maskFull = getSimpleBitmap(context, template.mask, 1)

        return TemplateData(
            listOf(mainFull, mainEmpty, level),
            listOf(maskLevel, maskFull),
            listOf()
        )
    }

}