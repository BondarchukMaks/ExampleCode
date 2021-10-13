package com.digicode.dodobattery.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.digicode.dodobattery.di.key.ViewModelKey
import com.digicode.dodobattery.factory.ViewModelFactory
import com.digicode.dodobattery.viewModel.main.MainViewModel
import com.digicode.dodobattery.viewModel.menu.MenuViewModel
import com.digicode.dodobattery.viewModel.privacy.PrivacyViewModel
import com.digicode.dodobattery.viewModel.singleActivity.SingleActivityViewModel
import com.digicode.dodobattery.viewModel.terms.TermsViewModel
import com.digicode.dodobattery.viewModel.tutorial.TutorialViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SingleActivityViewModel::class)
    internal abstract fun bindSingleViewModel(viewModel: SingleActivityViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TutorialViewModel::class)
    internal abstract fun bindTutorialViewModel(viewModel: TutorialViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TermsViewModel::class)
    internal abstract fun bindTermsViewModel(viewModel: TermsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PrivacyViewModel::class)
    internal abstract fun bindPrivacyViewModel(viewModel: PrivacyViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MenuViewModel::class)
    internal abstract fun bindMenuViewModel(viewModel: MenuViewModel) : ViewModel


}