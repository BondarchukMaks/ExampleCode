package com.digicode.dodobattery.graphic.drawable

import android.graphics.*
import android.graphics.drawable.Drawable

abstract class BaseDrawable : Drawable() {

    protected val defaultPaint = Paint()

    init {
        defaultPaint.flags = Paint.ANTI_ALIAS_FLAG;
        defaultPaint.isAntiAlias = true;
        defaultPaint.isFilterBitmap = true;
    }

    protected fun drawLock(
        locked: Pair<Bitmap, Boolean>,
        canvas: Canvas
    ) {
        val show = locked.second
        val lockBitmap = locked.first

        if (show) {
            val xPosition = (canvas.width - lockBitmap.width).toFloat()
            canvas.drawBitmap(lockBitmap, xPosition, 0.0f, defaultPaint)
        }
    }

    protected fun drawWatermark(
        previewTitle: Pair<String, Boolean>,
        previewDescription: Pair<String, Boolean>,
        canvas: Canvas,
        leftMarging: Pair<Float, Float>
    ) {
        var show = previewTitle.second
        if (show) {
            val canvasWidth = canvas.width.toFloat()
            val canvasHeight = canvas.height.toFloat()

            val backPaint = Paint()
            backPaint.color = Color.rgb(255, 255, 255)
            backPaint.alpha = 170

            val backgroundTop = calculateY(canvasHeight, 35f)
            val backgroundBottom = calculateY(canvasHeight, 65f)

            val rect =  Rect(
                0,
                backgroundTop.toInt(),
                canvasWidth.toInt(),
                backgroundBottom.toInt()
            )

            val corners = floatArrayOf(
                15f, 15f,
                15f, 15f,
                15f, 15f,
                15f, 15f
            )

            val path = Path()
            path.addRoundRect(RectF(rect), corners, Path.Direction.CW)
            canvas.drawPath(path, backPaint)

            val paint = Paint()
            val titleTextSize = calculateY(canvasHeight, 13f)
            val titleWidhtPos = calculateX(canvasWidth, 22f);
            val titleTopPos = calculateY(canvasHeight, 50f)
            paint.color = Color.BLACK
            paint.alpha = 200;
            paint.isAntiAlias = true;
            paint.textSize = titleTextSize

            canvas.drawText(previewTitle.first, titleWidhtPos, titleTopPos, paint)


            val descrTextSize = calculateY(canvasHeight, 7f)
            val descrTopPos = titleTopPos + titleTextSize - calculateY(canvasHeight, 3f)
            paint.textSize = descrTextSize
            canvas.drawText(previewDescription.first, leftMarging.second, descrTopPos, paint)
        }
    }

    protected fun drawCharging(
        charging: Pair<Bitmap, Boolean>,
        canvas: Canvas
    ) {
        val show = charging.second
        if (show) {
            val topPos = calculateY(canvas.height.toFloat(), 25f)
            canvas.drawBitmap(charging.first,  (canvas.width - (charging.first.width* 2)).toFloat(), charging.first.height.toFloat() / 2, defaultPaint)
        }
    }

    open fun drawBatteryLevel(
        numbers: List<Bitmap>?,
        percents: Bitmap,
        canvas: Canvas,
        leftMarging: Float
    ) {
        val topPos = calculateY(canvas.height.toFloat(), 45f)
        drawBatteryLevel(numbers, percents, canvas, leftMarging, topPos)
    }

    protected var interSymbolSpacePercent = 0;

    protected open fun drawBatteryLevel(numbers: List<Bitmap>?,
                                   percents: Bitmap,
                                   canvas: Canvas,
                                   leftMarging: Float,
                                   topMarging: Float) {
        val batteryLevel = mutableListOf<Bitmap>()
        var leftMargin = leftMarging
        val percentHeight = percents.height
        val numbersHeight = numbers?.get(0)?.height;
        var percentsMarging = 0;

        numbersHeight?.let {
            percentsMarging = (numbersHeight - percentHeight) / 2;
        }

        numbers?.let { batteryLevel.addAll(it) }
        batteryLevel.forEach {
            canvas.drawBitmap(it, leftMargin, topMarging, defaultPaint)
            leftMargin += (it.width + interSymbolSpacePercent)
        }

        canvas.drawBitmap(percents, leftMargin, topMarging + percentsMarging, defaultPaint)
    }

    protected fun calculateX(canvasWidth: Float, percents: Float): Float {
       return canvasWidth / 100 * percents
    }

    protected fun calculateY(canvasHeight: Float, percents: Float): Float {
        return canvasHeight / 100 * percents
    }

    protected var topModifier = 0.6f;

    protected open fun calculateTop(canvasHeight: Int, batteryLevel: Int): Float {
        val top = (canvasHeight - (canvasHeight * batteryLevel / 100)) * topModifier
        return if (top > 0) {
            top
        } else {
            0.0f
        }
    }

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }

    override fun setAlpha(alpha: Int) {
        //Not implemented
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        //Not implemented
    }
}