package com.digicode.dodobattery.view.ui.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.digicode.dodobattery.R
import com.digicode.dodobattery.view.ui.base.activity.BaseActivity
import dagger.android.support.DaggerDialogFragment

abstract class BaseDialog : DaggerDialogFragment() {

    @LayoutRes
    abstract fun getLayoutRes(): Int

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

    open fun parseParameters(bundle: Bundle) {
        //skip for default
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (getLayoutRes() == BaseActivity.EMPTY_LAYOUT_RES_ID) {
            super.onCreateView(inflater, container, savedInstanceState)
        } else {
            val view = inflater.inflate(getLayoutRes(), container, false)
            view.setOnTouchListener { _, _ -> true }
            return view
        }
    }

    override fun onStart() {
        super.onStart()

        dialog?.let { dialog ->
            dialog.window?.let { window ->
                window.setDimAmount(0.6f)
                window.setWindowAnimations(getAnimationStyle())
                window.setBackgroundDrawableResource(getBackgroundDrawableResource())

                val attr = window.attributes
                attr.width = setWindowWidth()
                attr.height = setWindowHeight()
                window.attributes = attr
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    protected abstract fun initView()

    private fun getAnimationStyle() = R.style.DialogAnimation

    protected open fun getBackgroundDrawableResource() = R.drawable.bg_pop_up

    protected open fun setWindowWidth() = (resources.displayMetrics.widthPixels * 0.8).toInt()

    protected open fun setWindowHeight() = ViewGroup.LayoutParams.WRAP_CONTENT

    protected fun showToast(@StringRes resId: Int) {
        Toast.makeText(requireContext(), resId, Toast.LENGTH_SHORT).show()
    }

}