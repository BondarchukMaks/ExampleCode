package com.digicode.dodobattery.utils

import android.content.Context
import android.os.Build
import androidx.annotation.ColorRes

@Suppress("DEPRECATION")
fun getColor(context: Context, @ColorRes colorRes: Int): Int {
    return if (Build.VERSION.SDK_INT >= 23) {
        context.resources.getColor(colorRes, null)
    } else {
        context.resources.getColor(colorRes)
    }
}