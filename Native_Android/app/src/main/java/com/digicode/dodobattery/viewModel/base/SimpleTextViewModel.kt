package com.digicode.dodobattery.viewModel.base

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.toSpanned

abstract class SimpleTextViewModel :BaseViewModel()
{
    protected abstract val mainText : String

    fun getMainText() : Spanned {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(mainText, Html.FROM_HTML_MODE_LEGACY).trimEnd().toString()
                .replace("coolbatterywidgetapp@gmail.com.","").toSpanned()
        }
        else{
            return Html.fromHtml(mainText).trimEnd().toString()
                .replace("coolbatterywidgetapp@gmail.com.","").toSpanned()
        }
    }


}