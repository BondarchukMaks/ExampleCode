package com.digicode.dodobattery.view.ui.privacy

import com.digicode.dodobattery.view.ui.base.fragment.SimpleScrollTextFragment
import com.digicode.dodobattery.viewModel.privacy.PrivacyViewModel


class PrivacyPolicyFragment : SimpleScrollTextFragment<PrivacyViewModel>() {

    override fun getViewModelKey() = PrivacyViewModel::class

    override fun subscribeOnViewModelEvents() {
    }
}
