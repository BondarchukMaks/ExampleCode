package com.digicode.dodobattery.graphic.drawable

import android.graphics.*
import com.digicode.dodobattery.data.model.WidgetNameKey
import com.digicode.dodobattery.graphic.helpers.BaseHelper.*

open class MaskADrawable(
    private val widgetName: String,
    private val batteryLevel: Int,
    private val templateData: TemplateData,
    private val lockData: LockData,
    val batteryData: BatteryData
): BaseDrawable() {

    private lateinit var bufferBitmap: Bitmap
    private lateinit var bufferCanvas: Canvas
    private val paint = Paint()

    val canvasWidth: Int
    private val canvasHeight: Int

    init {
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        canvasWidth = templateData.maskBitmaps.let { it[0]?.width } ?: 0
        canvasHeight = templateData.maskBitmaps.let { it[0]?.height } ?:0

    }

    override fun draw(canvas: Canvas) {

        bufferBitmap = Bitmap.createBitmap(canvas.clipBounds.width(), canvas.clipBounds.height(), Bitmap.Config.ARGB_8888)
        bufferCanvas = Canvas(bufferBitmap)
        drawMain()
        drawMask()
        drawCover()
        drawLock(lockData.locked, bufferCanvas)

        if(!lockData.locked.second)
            drawCharging(batteryData.charging, bufferCanvas)


        val batteryMarging = calculateBatteryLeftMarging()
        drawBatteryLevel(batteryData.numbers, batteryData.percents, bufferCanvas, batteryMarging)

        val previewTitleMarging = calculateLeftMargin(11f, 15f)
        val previewDescMarging = calculateLeftMargin(8.5f, 9f)
        drawWatermark(
            lockData.previewTitle,
            lockData.previewDescription,
            bufferCanvas,
            Pair(previewTitleMarging, previewDescMarging)
        )

        canvas.drawBitmap(bufferBitmap, 0.0f,0.0f, defaultPaint)
    }


    protected open fun calculateBatteryLeftMarging(): Float {
        val numbersWidth = batteryData.numbers
            ?.sumBy { it.width }
            ?: 0

        return (canvasWidth.toFloat() / 2) - (numbersWidth / 2)
    }

    private fun drawMain() {
        val mainBitmap = templateData.mainBitmaps[0]
        if (mainBitmap != null) {
            val topPos = calculateTop(canvasHeight, batteryLevel)
            bufferCanvas.drawBitmap(mainBitmap, 0.0f, topPos, defaultPaint)
        }
    }

    private fun drawMask() {
        val mask = templateData.maskBitmaps[0]
        if (mask != null) {
            bufferCanvas.drawBitmap(mask, 0.0f, 0.0f, paint)
        }
    }

    private fun drawCover() {
        val cover = templateData.coverBitmaps[0]
        if (cover != null) {
            bufferCanvas.drawBitmap(cover, 0.0f, 0.0f, defaultPaint)
        }
    }

    private fun calculateLeftMargin(coffee: Float, aquarium: Float): Float {
        return if (widgetName == WidgetNameKey.COFFEE) {
            calculateX(canvasWidth.toFloat(), coffee)
        } else {
            calculateX(canvasWidth.toFloat(), aquarium)
        }
    }

    override fun getIntrinsicWidth(): Int {
        return canvasWidth
    }

    override fun getIntrinsicHeight(): Int {
        return canvasHeight
    }
}