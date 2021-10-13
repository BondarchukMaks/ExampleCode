package com.digicode.dodobattery.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import kotlin.reflect.KClass

class Utils {

    companion object {

        fun <T : Any> generateTag(modelClass: KClass<T>): String {
            return modelClass.java.name
        }
    }
}

inline fun <reified T : Enum<T>> String.asEnumOrDefault(defaultValue: T? = null): T? =
    enumValues<T>().firstOrNull { it.name.equals(this, ignoreCase = true) } ?: defaultValue
