package com.digicode.dodobattery.view.ui.main

import com.digicode.dodobattery.data.model.Widget

interface OnWidgetClickListener {

    fun onWidgetClick(widget: Widget)

    fun onExpandButtonClick(items: IntArray?, type: String)
}