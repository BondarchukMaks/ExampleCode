package com.digicode.dodobattery.graphic.helpers

import android.content.Context
import android.graphics.drawable.Drawable
import com.digicode.dodobattery.BitmapMemoryPool
import com.digicode.dodobattery.R
import com.digicode.dodobattery.data.model.Template
import com.digicode.dodobattery.data.model.Widget
import com.digicode.dodobattery.graphic.drawable.MaskADrawable
import com.digicode.dodobattery.graphic.drawable.MaskAqarium
import com.digicode.dodobattery.graphic.drawable.MaskDuck


class MaskAqaruiumHelper : MaskAHelper() {

    override fun create(
        context: Context,
        widget: Widget,
        batteryIndicators: Pair<Int, Boolean>,
        showWatermark: Boolean
    ): Drawable {
        val templateBitmaps = createTemplate(context, widget.template, batteryIndicators.first)
        val lockBitmaps = getLockData(context, widget.lockedStatus, showWatermark)
        val batteryBitmaps = getBatteryData(
            context,
            batteryIndicators,
            widget.template.numbers,
            widget.template.percents)

        return MaskAqarium(
            widget.name,
            batteryIndicators.first,
            templateBitmaps,
            lockBitmaps,
            batteryBitmaps
        )
    }

    override fun createTemplate(context: Context,
                                template: Template,
                                batteryLevel: Int): TemplateData {
        val main = template.mainImage?.let {
            val selectedImage = when (batteryLevel) {
                in 70..100 -> it[0]
                in 7..69 -> it[1]
                in 0..6 -> it[2]
                else -> R.drawable.ic_place_holder
            }

            BitmapMemoryPool.instance.getBitmapFromMemCache(selectedImage.toString(),context,selectedImage)
        }

        val mask = getSimpleBitmap(context, template.mask, 0)
        val cover = getSimpleBitmap(context, template.cover, 0)

        return TemplateData(
            listOf(main),
            listOf(mask),
            listOf(cover)
        )
    }
}