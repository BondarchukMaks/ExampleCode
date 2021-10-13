package com.digicode.dodobattery.utils.extensions

fun Int?.isMoreThenNull(): Boolean {
    return this != null && this > 0
}