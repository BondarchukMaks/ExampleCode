package com.digicode.dodobattery.graphic.helpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import com.digicode.dodobattery.BitmapMemoryPool
import com.digicode.dodobattery.R
import com.digicode.dodobattery.data.model.Widget
import com.digicode.dodobattery.graphic.drawable.MaskDDrawable

class MaskDHelper: BaseHelper() {

    override fun create(
        context: Context,
        widget: Widget,
        batteryIndicators: Pair<Int, Boolean>,
        showWatermark: Boolean
    ): Drawable {
        val templateBitmaps = createTemplate(context, widget)
        val lockBitmaps = getLockData(context, widget.lockedStatus, showWatermark)
        val batteryBitmaps = getBatteryData(
            context,
            batteryIndicators,
            widget.template.numbers,
            widget.template.percents)

        return MaskDDrawable(
            batteryIndicators.first,
            templateBitmaps,
            lockBitmaps,
            batteryBitmaps
        )
    }

    private fun createTemplate(context: Context,
                               widget: Widget
    ): TemplateData {
        val mainImage = getMainImage(context, widget)
        val maskLeft = getSimpleBitmap(context, widget.template.mask, 0)
        val maskRight = getSimpleBitmap(context, widget.template.mask, 1)
        val cover = getSimpleBitmap(context, widget.template.cover, 0)

        return TemplateData(
            listOf(mainImage),
            listOf(maskLeft, maskRight),
            listOf(cover)
        )
    }

    private fun getMainImage(
        context: Context,
        widget: Widget
    ): Bitmap? {
        return widget.template.mainImage
            ?.let {
                val selectedImage = widget.template.mainImage[widget.subTypes!!.first]
                BitmapMemoryPool.instance.getBitmapFromMemCache(selectedImage.toString(),context,selectedImage)
            }
    }
}