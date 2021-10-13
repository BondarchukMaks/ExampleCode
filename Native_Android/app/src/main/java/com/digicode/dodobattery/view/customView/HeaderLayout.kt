package com.digicode.dodobattery.view.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import at.favre.lib.dali.Dali
import com.digicode.dodobattery.R
import com.digicode.dodobattery.utils.extensions.show
import kotlinx.android.synthetic.main.layout_header.view.*

class HeaderLayout : LinearLayout {

    constructor(
        context: Context
    ) : this(context, null)

    constructor(
        context: Context,
        attributes: AttributeSet?
    ) : this(context, attributes, 0)

    constructor(
        context: Context,
        attributes:
        AttributeSet?,
        defStyle: Int
    ) : super(context, attributes, defStyle) {

        inflateLayout()
    }

    fun setButtonClickListener(listener: (v: View) -> Unit) {
        headerButton.setOnClickListener {
            listener(this)
        }
    }

    private fun inflateLayout() {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.layout_header, this, true)
    }
}