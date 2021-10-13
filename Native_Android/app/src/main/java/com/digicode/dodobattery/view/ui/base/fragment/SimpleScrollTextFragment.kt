package com.digicode.dodobattery.view.ui.base.fragment

import android.content.Intent
import com.digicode.dodobattery.R
import com.digicode.dodobattery.utils.extensions.popBackStack
import com.digicode.dodobattery.viewModel.base.SimpleTextViewModel
import kotlinx.android.synthetic.main.fragment_menu.headerLayout
import kotlinx.android.synthetic.main.fragment_terms.*

abstract class SimpleScrollTextFragment<T : SimpleTextViewModel> : BaseViewModelFragment<T>() {

    override fun getLayoutRes() = R.layout.fragment_terms

    override fun initView() {
        initListeners()

        mainTextView.text = viewModel.getMainText()
    }


    private fun initListeners() {
        headerLayout.setButtonClickListener {
            popBackStack()
        }

        emailText.setOnClickListener {
            sendEmail(emailText.text.toString())
        }
    }

    private fun sendEmail(email: String){

        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.type = "text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, email)
        startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
    }

}
