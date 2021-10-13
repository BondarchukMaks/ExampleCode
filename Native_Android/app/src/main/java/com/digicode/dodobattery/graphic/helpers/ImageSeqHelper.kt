package com.digicode.dodobattery.graphic.helpers

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import com.digicode.dodobattery.BitmapMemoryPool
import com.digicode.dodobattery.data.model.Template
import com.digicode.dodobattery.data.model.Widget
import com.digicode.dodobattery.data.model.WidgetNameKey
import com.digicode.dodobattery.graphic.drawable.ImageSeqDrawable

open class ImageSeqHelper: BaseHelper() {

    override fun create(
        context: Context,
        widget: Widget,
        batteryIndicators: Pair<Int, Boolean>,
        showWatermark: Boolean
    ): Drawable {
        val templateBitmaps = createTemplate(context, widget, batteryIndicators.first)
        val lockBitmaps = getLockData(context, widget.lockedStatus, showWatermark)
        val batteryBitmaps = getBatteryData(
            context,
            batteryIndicators,
            widget.template.numbers,
            widget.template.percents)

        return ImageSeqDrawable(
            templateBitmaps,
            lockBitmaps,
            batteryBitmaps
        )
    }

    fun createTemplate(
        context: Context,
        widget: Widget,
        batteryLevel: Int
    ): TemplateData {
        return if (widget.name == WidgetNameKey.DONUT) {
            getDonutMainImage(context, widget.template, batteryLevel)
        } else {
            getPizzaMainImage(context, widget.template, batteryLevel)
        }
    }

    private fun getDonutMainImage(context: Context,
                                  template: Template,
                                  batteryLevel: Int): TemplateData {
        val main = template.mainImage?.let {
            val selectedImage = when(batteryLevel) {
                in 99..100 -> it[0]
                in 96..98 -> it[1]
                in 91..95 -> it[2]
                in 86..90 -> it[3]
                in 81..85 -> it[4]
                in 76..80 -> it[5]
                in 71..75 -> it[6]
                in 66..70 -> it[7]
                in 61..65 -> it[8]
                in 55..60 -> it[9]
                in 51..55 -> it[10]
                in 46..50 -> it[11]
                in 41..45 -> it[12]
                in 36..40 -> it[13]
                in 31..35 -> it[14]
                in 26..30 -> it[15]
                in 21..25 -> it[16]
                in 16..20 -> it[17]
                in 11..15 -> it[18]
                in 6..10 -> it[19]
                else -> it[20]
            }
            BitmapMemoryPool.instance.getBitmapFromMemCache(selectedImage.toString(),context,selectedImage)
        }
        return TemplateData(listOf(main), listOf(), listOf())
    }

    private fun getPizzaMainImage(context: Context,
                                  template: Template,
                                  batteryLevel: Int): TemplateData {
        val main = template.mainImage?.let {
            val selectedImage = when(batteryLevel) {
                in 97..100 -> it[0]
                in 93..96 -> it[1]
                in 89..92 -> it[2]
                in 85..88 -> it[3]
                in 81..84 -> it[4]
                in 77..80 -> it[5]
                in 73..76 -> it[6]
                in 69..72 -> it[7]
                in 65..68 -> it[8]
                in 61..64 -> it[9]
                in 57..60 -> it[10]
                in 53..56 -> it[11]
                in 49..52 -> it[12]
                in 45..48 -> it[13]
                in 41..44 -> it[14]
                in 37..40 -> it[15]
                in 33..36 -> it[16]
                in 29..32 -> it[17]
                in 25..28 -> it[18]
                in 21..24 -> it[19]
                in 17..20 -> it[20]
                in 13..16 -> it[21]
                in 9..12 -> it[22]
                in 5..8 -> it[23]
                else -> it[24]
            }
            BitmapMemoryPool.instance.getBitmapFromMemCache(selectedImage.toString(),context,selectedImage)
        }
        return TemplateData(listOf(main), listOf(), listOf())
    }
}