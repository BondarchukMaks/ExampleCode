package com.digicode.dodobattery

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache

class BitmapMemoryPool {

    private object HOLDER {
        val INSTANCE = BitmapMemoryPool()
    }

    private var mMemoryCache: LruCache<String, Bitmap>? = null

    init {
        mMemoryCache =  LruCache(1024)
    }


    fun addBitmapToMemoryCache(key: String?, bitmap: Bitmap?) {
        mMemoryCache?.put(key, bitmap)
    }

    fun getBitmapFromMemCache(key: String?): Bitmap?{
        return mMemoryCache?.get(key)
    }

    fun getBitmapFromMemCache(key: String?, context: Context, defaultValue: Int): Bitmap{

        return let {
            val bitmap = mMemoryCache?.get(key)
            if(bitmap != null)
                bitmap
            else{
                val newBitmap = BitmapFactory.decodeResource(context.resources, defaultValue)
                addBitmapToMemoryCache(key,newBitmap)
                newBitmap
            }
        }
    }

    fun getBitmapFromMemCache(key: String?, lazyBitmap: () -> Bitmap): Bitmap {

        return let {
            val bitmap = mMemoryCache?.get(key)
            if(bitmap != null)
                bitmap
            else{
                val newBitmap = lazyBitmap.invoke()
                addBitmapToMemoryCache(key,newBitmap)
                newBitmap
            }

        }
    }

    fun containsKey(key:String?) : Boolean{
        return mMemoryCache?.get(key) != null
    }

    companion object {
        val instance: BitmapMemoryPool by lazy { HOLDER.INSTANCE }
    }
}