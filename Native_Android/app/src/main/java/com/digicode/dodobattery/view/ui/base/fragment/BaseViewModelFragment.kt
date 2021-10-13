package com.digicode.dodobattery.view.ui.base.fragment

import android.content.Context
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.digicode.dodobattery.R
import com.digicode.dodobattery.factory.ViewModelFactory
import com.digicode.dodobattery.viewModel.base.BaseViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseViewModelFragment<T : BaseViewModel> : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected lateinit var viewModel: T

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)

        initViewModel()
    }

    protected open fun initViewModel() {
        viewModel = getViewModel(getViewModelKey())
    }

    protected abstract fun getViewModelKey(): KClass<T>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeOnViewModelEvents()
        subscribeOnViewModelErrorEvents()
    }

    abstract fun subscribeOnViewModelEvents()

    protected fun <T : ViewModel> getActivityViewModel(@NonNull modelClass: KClass<T>): T {
        return ViewModelProvider(requireActivity(), viewModelFactory)[modelClass.java]
    }

    protected fun <T : ViewModel> getViewModel(@NonNull modelClass: KClass<T>): T {
        return ViewModelProvider(this, viewModelFactory)[modelClass.java]
    }

    private fun subscribeOnViewModelErrorEvents() {
        viewModel.getAppError().observeNonNull {
            showToast(R.string.error)
        }
    }

    fun <T> LiveData<T>.observeNonNull(observer: (T) -> Unit) =
        observe { if (it != null) observer(it) }

    fun <T> LiveData<T>.observe(observer: (T?) -> Unit) =
        observe(viewLifecycleOwner, Observer { observer(it) })

}