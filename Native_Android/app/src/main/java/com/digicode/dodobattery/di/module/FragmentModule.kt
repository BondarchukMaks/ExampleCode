package com.digicode.dodobattery.di.module

import com.digicode.dodobattery.service.FBMessagingService
import com.digicode.dodobattery.view.ui.dialog.FreeWidgetDialog
import com.digicode.dodobattery.view.ui.dialog.RateUsDialog
import com.digicode.dodobattery.view.ui.dialog.ShareDialog
import com.digicode.dodobattery.view.ui.main.MainFragment
import com.digicode.dodobattery.view.ui.menu.MenuFragment
import com.digicode.dodobattery.view.ui.privacy.PrivacyPolicyFragment
import com.digicode.dodobattery.view.ui.terms.TermsFragment
import com.digicode.dodobattery.view.ui.tutorial.TutorialFragment
import com.digicode.dodobattery.view.ui.zodiacs.ZodiacsDialog
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment() : MainFragment

    @ContributesAndroidInjector
    abstract fun contributeMenuFragment() : MenuFragment

    @ContributesAndroidInjector
    abstract fun contributePrivacyFragment() : PrivacyPolicyFragment

    @ContributesAndroidInjector
    abstract fun contributeTermsFragment() : TermsFragment

    @ContributesAndroidInjector
    abstract fun contributeTutorialFragment() : TutorialFragment

    //Dialogs
    @ContributesAndroidInjector
    abstract fun contributeZodiacsDialog() : ZodiacsDialog

    @ContributesAndroidInjector
    abstract fun contributeRateUsDialog() : RateUsDialog

    @ContributesAndroidInjector
    abstract fun contributeFreeWidgetDialog() : FreeWidgetDialog

    @ContributesAndroidInjector
    abstract fun contributeShareDialog() : ShareDialog

    @ContributesAndroidInjector
    abstract fun contributeFBMessagingService(): FBMessagingService
}