package com.digicode.dodobattery.graphic.helpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import com.digicode.dodobattery.BitmapMemoryPool
import com.digicode.dodobattery.R
import com.digicode.dodobattery.data.model.Widget
import com.digicode.dodobattery.data.model.enums.LockedStatus
import com.digicode.dodobattery.utils.Constants

abstract class BaseHelper {

    abstract fun create(context: Context,
                                  widget: Widget,
                                  batteryIndicators: Pair<Int, Boolean>,
                                  showWatermark: Boolean): Drawable

    protected fun getSimpleBitmap(context: Context,
                                  items: IntArray?,
                                  index: Int): Bitmap? {
        return items?.let {
            val selected = it[index]
            BitmapMemoryPool.instance.getBitmapFromMemCache(selected.toString(),context,selected)
        }
    }

    protected fun getLockData(context: Context,
                              locked: LockedStatus,
                              showWatermark: Boolean): LockData {



        val lockBitmap = BitmapMemoryPool.instance.getBitmapFromMemCache(Constants.LOCK_BITMAP,context,R.drawable.ic_widget_lock)

        val previewTitle = context.getString(R.string.preview_title)
        val previewDescription = context.getString(R.string.preview_desc)

        val showLock = locked == LockedStatus.LOCKED
        val showPreview = locked == LockedStatus.LOCKED && showWatermark

        return LockData(
            Pair(lockBitmap, showLock),
            Pair(previewTitle, showPreview),
            Pair(previewDescription, showPreview)
        )
    }

    protected fun getBatteryData(context: Context,
                                 batteryIndicators: Pair<Int, Boolean>,
                                 numbers: IntArray?,
                                 percents: Int): BatteryData {
        val numbersBitmap = getNumberBitmaps(context, batteryIndicators.first, numbers)
        val percentsBitmap = BitmapMemoryPool.instance.getBitmapFromMemCache(percents.toString(),context,percents)
        val chargingBitmap = BitmapMemoryPool.instance.getBitmapFromMemCache(Constants.CHARGING_BITMAP,context,R.drawable.ic_charging)

        return BatteryData(
            numbersBitmap,
            percentsBitmap,
            Pair(chargingBitmap, batteryIndicators.second)
        )
    }

    private fun getNumberBitmaps(context: Context,
                                 level: Int,
                                 items: IntArray?): List<Bitmap>? {
        return items?.let { images ->
            val strLevel = level.toString()
            val numbersBitmap = mutableListOf<Bitmap>()
            strLevel.forEach { c ->
                val digitResId = parseDigit(c, images)
                if (digitResId != null) {
                    val digitImage = BitmapMemoryPool.instance.getBitmapFromMemCache(digitResId.toString(),context,digitResId)
                    numbersBitmap.add(digitImage)
                }
            }
            numbersBitmap.toList()
        }
    }

    private fun parseDigit(digit: Char,
                           items: IntArray): Int? {
        return when(digit){
            '0' -> items[0]
            '1' -> items[1]
            '2' -> items[2]
            '3' -> items[3]
            '4' -> items[4]
            '5' -> items[5]
            '6' -> items[6]
            '7' -> items[7]
            '8' -> items[8]
            '9' -> items[9]
            else -> null
        }
    }

    class LockData(
        val locked: Pair<Bitmap, Boolean>,
        val previewTitle: Pair<String, Boolean>,
        val previewDescription: Pair<String, Boolean>
    )

    class BatteryData(
        var numbers: List<Bitmap>?,
        var percents: Bitmap,
        val charging: Pair<Bitmap, Boolean>
    )

    class TemplateData(
        val mainBitmaps: List<Bitmap?>,
        val maskBitmaps: List<Bitmap?>,
        val coverBitmaps: List<Bitmap?>
    )
}