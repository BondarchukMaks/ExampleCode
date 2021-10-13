package com.digicode.dodobattery.view.ui.dialog

import android.os.Bundle
import com.digicode.dodobattery.R
import com.digicode.dodobattery.utils.Constants
import com.digicode.dodobattery.utils.extensions.show
import com.digicode.dodobattery.view.ui.base.fragment.BaseDialog
import kotlinx.android.synthetic.main.dialog_free_widget.*
import kotlinx.android.synthetic.main.dialog_rate_us.confirmButton

class FreeWidgetDialog : BaseDialog() {

    private var isNewUser = false

    override fun getLayoutRes() = R.layout.dialog_free_widget

    override fun initView() {
        setDescriptionView()
        initListeners()
    }

    override fun parseParameters(bundle: Bundle) {
        super.parseParameters(bundle)
        isNewUser = bundle.getBoolean(Constants.IS_NEW_USER)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(Constants.IS_NEW_USER, isNewUser)
    }

    private fun setDescriptionView() {
        descriptionView.show(isNewUser)
    }

    private fun initListeners() {
        confirmButton.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        fun newInstance(isNewUser: Boolean): FreeWidgetDialog {
            val arguments = Bundle()
            arguments.putBoolean(Constants.IS_NEW_USER, isNewUser)

            val fragment = FreeWidgetDialog()
            fragment.arguments = arguments
            return fragment
        }
    }
}