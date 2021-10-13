package com.digicode.dodobattery.view.ui.menu

import com.digicode.dodobattery.R
import com.digicode.dodobattery.data.repository.AppRepository
import com.digicode.dodobattery.utils.extensions.popBackStack
import com.digicode.dodobattery.view.navigation.NavRouter
import com.digicode.dodobattery.view.ui.base.fragment.BaseFragment
import com.digicode.dodobattery.view.ui.base.fragment.BaseViewModelFragment
import com.digicode.dodobattery.viewModel.main.MainViewModel
import com.digicode.dodobattery.viewModel.menu.MenuViewModel
import kotlinx.android.synthetic.main.fragment_menu.*
import javax.inject.Inject

class MenuFragment : BaseViewModelFragment<MenuViewModel>(){



    override fun getLayoutRes() = R.layout.fragment_menu

    override fun getViewModelKey() = MenuViewModel::class

    override fun subscribeOnViewModelEvents() {
    }

    override fun initView() {
        initListeners()
    }

    private fun initListeners() {
        headerLayout.setButtonClickListener {
            popBackStack()
        }
        shareAndGetView.setOnClickListener {

            viewModel.getShareLink{
                NavRouter.showShareDialog(this,it)
            }

        }
        howToAddView.setOnClickListener {
            NavRouter.startTutorialScreen(this)
        }
        rateUsView.setOnClickListener {
            NavRouter.showRateUsDialog(this)
        }
        termsAndCondView.setOnClickListener {
            NavRouter.startTermsScreen(this)
        }
        privacyPolicyView.setOnClickListener {
            NavRouter.startPrivacyScreen(this)
        }
    }
}
