package com.digicode.dodobattery

import androidx.multidex.MultiDexApplication
import com.digicode.dodobattery.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class App : MultiDexApplication(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()
        buildDagger()
    }

    private fun buildDagger() {
        DaggerAppComponent.builder()
            .context(this)
            .application(this)
            .build()
            .inject(this)
    }

}