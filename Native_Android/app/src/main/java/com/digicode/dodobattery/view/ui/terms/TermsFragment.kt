package com.digicode.dodobattery.view.ui.terms

import com.digicode.dodobattery.view.ui.base.fragment.SimpleScrollTextFragment
import com.digicode.dodobattery.viewModel.terms.TermsViewModel

class TermsFragment : SimpleScrollTextFragment<TermsViewModel>() {

    override fun getViewModelKey() = TermsViewModel::class

    override fun subscribeOnViewModelEvents() {
    }
}