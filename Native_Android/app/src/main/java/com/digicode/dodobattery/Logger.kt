package com.digicode.dodobattery

import android.content.Context
import android.os.Bundle
import com.digicode.dodobattery.di.qualifier.AppContext
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject


class Logger @Inject constructor(
    @AppContext private val context: Context
) {

    private val firebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    public fun log(name: String , bundle: Bundle){
        firebaseAnalytics.logEvent(name, bundle)
    }

    public fun log(name: String , value : Pair<String,String?>){
        val bundle = Bundle()
        bundle.putString(value.first,value.second)
        log(name,bundle)
    }

    @JvmName("log1")
    public fun log(name: String, valueInt : Pair<String,Int>){
        val bundle = Bundle()
        bundle.putInt(valueInt.first,valueInt.second)
        log(name,bundle)
    }

}