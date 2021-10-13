package com.digicode.dodobattery.view.ui.base.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection

abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    abstract fun getLayoutRes(): Int

    protected abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        if (getLayoutRes() != EMPTY_LAYOUT_RES_ID) {
            setContentView(getLayoutRes())
        }

        initView()
    }

    companion object {

        const val EMPTY_LAYOUT_RES_ID = -1

    }
}