package com.digicode.dodobattery.di.module

import com.digicode.dodobattery.view.ui.activity.SingleActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeSingleActivity() : SingleActivity

}