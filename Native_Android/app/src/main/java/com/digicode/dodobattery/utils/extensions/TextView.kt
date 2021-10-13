package com.digicode.dodobattery.utils.extensions

import android.graphics.Paint
import android.widget.TextView

fun TextView.setUnderline() {
    this.paintFlags = Paint.UNDERLINE_TEXT_FLAG
}