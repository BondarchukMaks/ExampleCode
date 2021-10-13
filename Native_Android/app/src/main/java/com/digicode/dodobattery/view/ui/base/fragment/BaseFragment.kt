package com.digicode.dodobattery.view.ui.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.digicode.dodobattery.view.ui.base.activity.BaseActivity
import dagger.android.support.AndroidSupportInjection
import kotlin.reflect.KClass

abstract class BaseFragment: Fragment(){

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            parseParameters(savedInstanceState)
        } else {
            val args = arguments
            if (args != null && !args.isEmpty) {
                parseParameters(args)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            parseParameters(savedInstanceState)
        }
    }

    open fun parseParameters(bundle: Bundle) {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (getLayoutRes() == BaseActivity.EMPTY_LAYOUT_RES_ID) {
            super.onCreateView(inflater, container, savedInstanceState)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
            val view = inflater.inflate(getLayoutRes(), container, false)
            view.setOnTouchListener { _, _ -> true }
            return view
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    protected abstract fun initView()

    fun <T : Fragment> generateTag(modelClass: KClass<T>): String {
        return modelClass.java.name
    }

    fun <T : Fragment> getSimpleName(modelClass: KClass<T>): String {
        return modelClass.java.simpleName
    }

    protected fun showToast(@StringRes resId: Int) {
        val toast = Toast.makeText(requireContext(), resId, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}