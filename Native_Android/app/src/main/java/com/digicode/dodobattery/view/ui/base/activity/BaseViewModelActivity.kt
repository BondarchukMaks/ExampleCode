package com.digicode.dodobattery.view.ui.base.activity

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.digicode.dodobattery.viewModel.base.BaseViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseViewModelActivity<T : BaseViewModel> : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = getViewModel(getViewModelKey())

        subscribeOnViewModelEvents()
        subscribeOnViewModelErrorEvents()
    }

    abstract fun getViewModelKey(): KClass<T>

    abstract fun subscribeOnViewModelEvents()

    private fun <T : ViewModel> getViewModel(@NonNull modelClass: KClass<T>): T {
        return ViewModelProvider(this, viewModelFactory)[modelClass.java]
    }

    private fun subscribeOnViewModelErrorEvents() {
//        viewModel.stringErrorLiveData.observeNonNull {
//            showError(it)
//        }
//
//        viewModel.resourceErrorLiveData.observeNonNull {
//            showError(it)
//        }
    }

    fun <T> LiveData<T>.observeNonNull(observer: (T) -> Unit) =
        observe { if (it != null) observer(it) }

    fun <T> LiveData<T>.observe(observer: (T?) -> Unit) =
        observe(this@BaseViewModelActivity, Observer { observer(it) })
}