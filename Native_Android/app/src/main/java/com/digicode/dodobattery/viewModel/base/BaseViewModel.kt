package com.digicode.dodobattery.viewModel.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel(){

    private val appError = MutableLiveData<Throwable>()

    fun getAppError(): LiveData<Throwable> = appError

    protected fun showError(appError: Throwable) {
        this.appError.value = appError
    }

    protected fun <T> toLiveData(
        liveData: MutableLiveData<T>
    ): LiveData<T> = liveData





}