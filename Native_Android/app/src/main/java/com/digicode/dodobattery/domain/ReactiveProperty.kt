package com.digicode.dodobattery.domain

open class ReactiveProperty<T>(initValue: T) {

    private var _value: T = initValue;
    private val _listeners = mutableListOf<((T?)->Unit)?>()

    fun setValue(value : T){
        _value = value;

        _listeners.forEach {
            it?.invoke(value)
        }
    }

    fun setSilentValue(value : T){
        _value = value;
    }

    fun getValue() : T {
        return _value;
    }

    fun observable(listener : ((T?)->Unit)? = null){
        _listeners.add(listener)
    }


    /*
    var text: T by Delegates.observable(
        initialValue = initValue,
        onChange = {
                property, oldValue, newValue ->
            callNotify() }
    )

    private fun callNotify(){
        print("dqwdwqwd")
    }*/
}
