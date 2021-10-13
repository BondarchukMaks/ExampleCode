package com.digicode.dodobattery.graphic.drawable

import android.graphics.*
import com.digicode.dodobattery.graphic.helpers.BaseHelper.*
import com.digicode.dodobattery.utils.extensions.isMoreThenNull

open class MaskBDrawable(
    private val batteryLevel: Int,
    private val templateData: TemplateData,
    private val lockData: LockData,
    private val batteryData: BatteryData
): BaseDrawable() {

    private lateinit var bufferBitmap: Bitmap
    private lateinit var bufferCanvas: Canvas
    private val paint = Paint()

    private val canvasWidth: Int
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
        drawTube()
        drawCover()
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

        canvas.drawBitmap(bufferBitmap, 0.0f,0.0f, defaultPaint)
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

    private fun drawTube() {
        val tube = templateData.mainBitmaps[1]
        val mask = templateData.maskBitmaps[1]
        if (tube != null && mask != null) {
            val scaledTube = Bitmap.createBitmap(tube.width, tube.height, Bitmap.Config.ARGB_8888)
            val tubeCanvas = Canvas(scaledTube)
            tubeCanvas.drawBitmap(tube, 0.0f, 0.0f, null)

            val top = calculateTop(canvasHeight, batteryLevel)

            val tubePaint = Paint()
            tubePaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            tubeCanvas.drawBitmap(mask, 0.0f, top, tubePaint)

            bufferCanvas.drawBitmap(scaledTube, 0.0f, 0.0f, defaultPaint)
        }
    }

    private fun calculateBatteryLeftMarging(): Float {
        val numbersWidth = batteryData.numbers
            ?.sumBy { it.width }
            ?.plus(batteryData.percents.width) ?: 0

        return canvasWidth.toFloat() / 2 - numbersWidth / 2
    }

    private fun drawCover() {
        val cover = templateData.coverBitmaps[0]
        if (cover != null) {
            bufferCanvas.drawBitmap(cover, 0.0f, 0.0f, defaultPaint)
        }
    }

    override fun getIntrinsicWidth(): Int {
        return canvasWidth
    }

    override fun getIntrinsicHeight(): Int {
        return canvasHeight
    }
}

