package com.digicode.dodobattery.view.ui.dialog

import android.content.Intent
import com.digicode.dodobattery.R
import com.digicode.dodobattery.view.ui.base.fragment.BaseDialog
import kotlinx.android.synthetic.main.dialog_share.*

interface IShareLink {
    fun shareLink(text: String)
}

class ShareDialog(
    private val deepLink: String
) : BaseDialog(), IShareLink{


    override fun getLayoutRes() = R.layout.dialog_share

    override fun initView() {
        initListeners()
    }

    override fun getBackgroundDrawableResource() = R.drawable.bg_white

    private fun initListeners() {

        shareButton.setOnClickListener {
            shareLink(deepLink)
            dismiss()
        }

        closeView.setOnClickListener {
            dismiss()
        }
    }

    override fun shareLink(text: String){
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type="text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(shareIntent, ""))
    }


    companion object {
        fun newInstance(deepLink: String): ShareDialog {
            return ShareDialog(deepLink)
        }
    }
}