package com.digicode.dodobattery.factory

import kotlin.properties.Delegates


class WidgetStatusObserver(initValue: Pair<String,String>){

    /*
    private val _listeners = mutableListOf<((Pair<String,String>)->Unit)?>()

    var text: Pair<String,String> by Delegates.observable(
        initialValue = initValue,
        onChange = {
                property, oldValue, newValue ->
            notify(newValue) }
    )

    fun addListener(listener: ValueChallengeListener) {


        listeners.add(listener)
    }

    fun removeListener(listener: ValueChallengeListener) {
        listeners.remove(listener)
    }

    private fun notify(t: Pair<String,String>) {
        for (o in listeners)
            o.valueChanged(text)
    }*/
}