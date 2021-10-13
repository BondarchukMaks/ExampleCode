package com.digicode.dodobattery.graphic.drawable

import android.graphics.*

import com.digicode.dodobattery.graphic.helpers.BaseHelper
import com.digicode.dodobattery.utils.extensions.isMoreThenNull

class MaskDDrawable(
    private val batteryLevel: Int,
    private val templateData: BaseHelper.TemplateData,
    private val lockData: BaseHelper.LockData,
    private val batteryData: BaseHelper.BatteryData
): BaseDrawable() {

    private lateinit var bufferBitmap: Bitmap
    private lateinit var bufferCanvas: Canvas
    private val paint = Paint()

    private val canvasWidth: Int
    private val canvasHeight: Int

    init {
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        canvasWidth = templateData.coverBitmaps.let { it[0]?.width } ?: 0
        canvasHeight = templateData.coverBitmaps.let { it[0]?.height } ?:0
    }

    override fun draw(canvas: Canvas) {

        bufferBitmap = Bitmap.createBitmap(canvas.clipBounds.width(), canvas.clipBounds.height(), Bitmap.Config.ARGB_8888)
        bufferCanvas = Canvas(bufferBitmap)
        drawCover()
        drawMain()
        drawLeftProgress()
        drawRightProgress()
        drawLock(lockData.locked, bufferCanvas)

        if(!lockData.locked.second)
            drawCharging(batteryData.charging, bufferCanvas)

        val batteryLeftMarging = calculateBatteryLeftMarging()
        val batteryTopMarging = calculateBatteryTopMarging()
        drawBatteryLevel(
            batteryData.numbers,
            batteryData.percents,
            bufferCanvas,
            batteryLeftMarging,
            batteryTopMarging
        )

        val previewTitleMarging = calculateX(canvasWidth.toFloat(), 15f)
        val previewDescMarging = calculateX(canvasWidth.toFloat(), 9f)
        drawWatermark(
            lockData.previewTitle,
            lockData.previewDescription,
            bufferCanvas,
            Pair(previewTitleMarging, previewDescMarging)
        )

        canvas.drawBitmap(bufferBitmap, 0.0f,0.0f, defaultPaint)
    }

    private fun drawCover() {
        val cover = templateData.coverBitmaps[0]
        if (cover != null) {
            bufferCanvas.drawBitmap(cover, 0.0f, 0.0f, defaultPaint)
        }
    }

    private fun drawMain() {
        val mainBitmap = templateData.mainBitmaps[0]
        if (mainBitmap != null) {
            val topPos = calculateMainTopMarging(mainBitmap.height)
            bufferCanvas.drawBitmap(mainBitmap, 0.0f, topPos, defaultPaint)
        }
    }

    private fun drawLeftProgress() {
        val mask = templateData.maskBitmaps[0]
        if (mask != null) {
            val scaledBitmap = Bitmap.createBitmap(mask.width, mask.height, Bitmap.Config.ARGB_8888)
            val scaledCanvas = Canvas(scaledBitmap)

            scaledCanvas.drawBitmap(mask, 0.0f, 0.0f, defaultPaint)

            if (batteryLevel <= 50) {
                val angle = batteryLevel * 3.2f
                drawMask(scaledCanvas, mask, angle)
            }

            bufferCanvas.drawBitmap(scaledBitmap, 0.0f, 0.0f, defaultPaint)
        }
    }

    private fun drawRightProgress() {
        val mask = templateData.maskBitmaps[1]
        if (mask != null && batteryLevel > 50) {
            val scaledBitmap = Bitmap.createBitmap(mask.width, mask.height, Bitmap.Config.ARGB_8888)
            val scaledCanvas = Canvas(scaledBitmap)

            scaledCanvas.drawBitmap(mask, 0.0f, 0.0f, defaultPaint)

            val angle = (batteryLevel - 50) * 3.2f

            drawMask(scaledCanvas, mask, angle)

            bufferCanvas.drawBitmap(scaledBitmap, 0.0f, 0.0f, defaultPaint)
        }
    }

    private fun drawMask(scaledCanvas: Canvas, mask: Bitmap, angle: Float) {
        val matrix = Matrix()
        val centerX = (mask.width / 2).toFloat()
        val centerY = (mask.height / 2).toFloat()
        matrix.postRotate(angle, centerX, centerY)

        val scaledRotatedBitmap = Bitmap.createBitmap(mask.width, mask.height, Bitmap.Config.ARGB_8888)
        val scaledRotatedCanvas = Canvas(scaledRotatedBitmap)
        val rotatedMask = Bitmap.createScaledBitmap(mask, mask.width, mask.height, true)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

        scaledRotatedCanvas.drawBitmap(mask, 0.0f, 0.0f, defaultPaint)
        scaledRotatedCanvas.drawBitmap(rotatedMask, matrix, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.XOR)
        scaledCanvas.drawBitmap(scaledRotatedBitmap, 0.0f, 0.0f, paint)
    }

    private fun calculateMainTopMarging(mainHeight: Int): Float {
        return ((canvasHeight / 2) - ( mainHeight / 2)).toFloat()
    }

    private fun calculateBatteryTopMarging(): Float {
        return canvasHeight - calculateY(canvasHeight.toFloat(), 15f)
    }

    private fun calculateBatteryLeftMarging(): Float {
        val numbersWidth = batteryData.numbers
            ?.sumBy { it.width }
            ?: 0

        return (canvasWidth.toFloat() / 2) - (numbersWidth / 2)
    }

    override fun getIntrinsicWidth(): Int {
        return canvasWidth
    }

    override fun getIntrinsicHeight(): Int {
        return canvasHeight
    }


}