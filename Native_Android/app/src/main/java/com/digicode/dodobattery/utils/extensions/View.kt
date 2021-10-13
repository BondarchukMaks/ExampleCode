package com.digicode.dodobattery.utils.extensions

import android.view.View

fun View.show(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.INVISIBLE
}