package com.digicode.dodobattery.graphic.helpers

import android.content.Context
import android.graphics.drawable.Drawable
import com.digicode.dodobattery.data.model.Template
import com.digicode.dodobattery.data.model.Widget
import com.digicode.dodobattery.graphic.drawable.MaskBDrawable

open class MaskBHelper: BaseHelper() {

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

        return MaskBDrawable(
            batteryIndicators.first,
            templateBitmaps,
            lockBitmaps,
            batteryBitmaps
        )
    }

    fun createTemplate(context: Context,
                               template: Template): TemplateData {
        val main = getSimpleBitmap(context, template.mainImage, 0)
        val tube = getSimpleBitmap(context, template.mainImage, 1)
        val mask = getSimpleBitmap(context, template.mask, 0)
        val mask2 = getSimpleBitmap(context, template.mask, 1)
        val cover = getSimpleBitmap(context, template.cover, 0)

        return TemplateData(
            listOf(main, tube),
            listOf(mask, mask2),
            listOf(cover)
        )
    }

}