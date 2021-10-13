package com.digicode.dodobattery.graphic.drawable

import android.graphics.*
import com.digicode.dodobattery.graphic.helpers.BaseHelper
import com.digicode.dodobattery.utils.extensions.isMoreThenNull

open class ImageSeqDrawable(
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
        canvasWidth = templateData.mainBitmaps.let { it[0]?.width } ?: 0
        canvasHeight = templateData.mainBitmaps.let { it[0]?.height } ?:0
    }

    override fun draw(canvas: Canvas) {
        drawMain()
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

        canvas.drawBitmap(bufferBitmap, 0.0f,0.0f, null)
    }

    override fun drawBatteryLevel(
        numbers: List<Bitmap>?,
        percents: Bitmap,
        canvas: Canvas,
        leftMarging: Float
    ) {
        val topPos = calculateY(canvas.height.toFloat(), 43f)
        drawBatteryLevel(numbers, percents, canvas, leftMarging, topPos)
    }

    private fun drawMain() {
        val mainBitmap = templateData.mainBitmaps[0]
        if (mainBitmap != null) {
            bufferCanvas.drawBitmap(mainBitmap, 0.0f, 0.0f, null)
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

    override fun onBoundsChange(bounds: Rect?) {
        super.onBoundsChange(bounds)
        val width = bounds?.width()
        val height = bounds?.height()

        if (width.isMoreThenNull() || height.isMoreThenNull()) {
            bufferBitmap = Bitmap.createBitmap(width!!, height!!, Bitmap.Config.ARGB_8888)
            bufferCanvas = Canvas(bufferBitmap)
        }
    }
}