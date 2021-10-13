package com.digicode.dodobattery.graphic.drawable

import android.graphics.*
import android.graphics.Shader.TileMode
import com.digicode.dodobattery.graphic.helpers.BaseHelper.*
import kotlin.math.roundToInt


open class MaskCDrawable(
    val batteryLevel: Int,
    private val templateData: TemplateData,
    private val lockData: LockData,
    var batteryData: BatteryData
): BaseDrawable() {

    private lateinit var bufferBitmap: Bitmap
    private lateinit var bufferCanvas: Canvas
    private val paint = Paint()

    private val canvasWidth: Int
    val canvasHeight: Int

    init {
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        canvasWidth = templateData.maskBitmaps.let { it[0]?.width } ?: 0
        canvasHeight = templateData.maskBitmaps.let { it[0]?.height } ?:0
    }

    override fun draw(canvas: Canvas) {

        bufferBitmap = Bitmap.createBitmap(
            canvas.clipBounds.width(),
            canvas.clipBounds.height(),
            Bitmap.Config.ARGB_8888
        )
        bufferCanvas = Canvas(bufferBitmap)


        drawEmpty()
        drawFull()
        drawLevel()

        drawLock(lockData.locked, bufferCanvas)

        if(!lockData.locked.second)
            drawCharging(batteryData.charging, bufferCanvas)


        val batteryMarging = calculateBatteryLeftMarging()
        drawBatteryLevel(batteryData.numbers, batteryData.percents, bufferCanvas, batteryMarging)

        val previewTitleMarging = calculateX(canvasWidth.toFloat(), 10f)
        val previewDescMarging = calculateX(canvasWidth.toFloat(), 9f)
        drawWatermark(
            lockData.previewTitle,
            lockData.previewDescription,
            bufferCanvas,
            Pair(previewTitleMarging, previewDescMarging)
        )

        canvas.drawBitmap(bufferBitmap, 0.0f, 0.0f, defaultPaint)
    }



    private fun drawEmpty() {
        val mainEmpty = templateData.mainBitmaps[1]
        if (mainEmpty != null) {

            bufferCanvas.drawBitmap(mainEmpty, null,Rect(0, 0,bufferCanvas.width,bufferCanvas.height), defaultPaint)
        }
    }

    private fun drawFull() {
        val mainFull = templateData.mainBitmaps[0]
        val mask = templateData.maskBitmaps[1]
        if (mainFull != null && mask != null) {
            val scaledFull = Bitmap.createBitmap(bufferBitmap.width , bufferBitmap.height, Bitmap.Config.ARGB_8888)

            val canvasFull = Canvas(scaledFull)
            canvasFull.drawBitmap(mainFull, null,Rect(0, 0,bufferCanvas.width,bufferCanvas.height), defaultPaint)

            val top = calculateTop(canvasHeight,batteryLevel)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
            canvasFull.drawBitmap(mask, 0.0f, top, paint)

            //clean top of canvas
            if (top >= 1) {
                val heightMask2 = top.roundToInt()
                val mask2 = Bitmap.createBitmap(canvasWidth, heightMask2, Bitmap.Config.ARGB_8888)
                canvasFull.drawBitmap(mask2, 0.0f, 0.0f, paint)
            }

            bufferCanvas.drawBitmap(scaledFull, null,Rect(0, 0,bufferCanvas.width,bufferCanvas.height), defaultPaint)
        }
    }



    private fun drawLevel() {
        val levelBitmap = templateData.mainBitmaps[2]
        val levelMask = templateData.maskBitmaps[0]
        if (levelBitmap != null && levelMask != null) {
            val scaledLevel = Bitmap.createBitmap(
                canvasWidth,
                canvasHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvasLevel = Canvas(scaledLevel)

            val top = calculateTop(canvasHeight,batteryLevel)
            canvasLevel.drawBitmap(levelBitmap, 0.0f, top, defaultPaint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
            canvasLevel.drawBitmap(levelMask, 0.0f, 0.0f, paint)

            bufferCanvas.drawBitmap(scaledLevel, 0.0f, 0.0f, defaultPaint)
        }
    }

    private fun calculateBatteryLeftMarging(): Float {
        val numbersWidth = batteryData.numbers
            ?.sumBy { it.width }
            ?.plus(batteryData.percents.width) ?: 0

        return canvasWidth.toFloat() / 2 - numbersWidth / 2
    }

    override fun getIntrinsicWidth(): Int {
        return canvasWidth
    }

    override fun getIntrinsicHeight(): Int {
        return canvasHeight
    }
}