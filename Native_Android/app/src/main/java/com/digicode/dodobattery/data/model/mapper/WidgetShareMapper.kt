package com.digicode.dodobattery.data.model.mapper

import com.digicode.dodobattery.data.model.WidgetNameKey
import com.digicode.dodobattery.utils.Constants
import javax.inject.Inject

class WidgetShareMapper @Inject constructor() {

    fun convertOptionToName(option: Pair<String, Boolean>): Pair<String, Boolean> {
        val name = when(option.first) {
            Constants.AQUARIUM_SHARE -> WidgetNameKey.AQUARIUM
            Constants.MANEKI_SHARE -> WidgetNameKey.MANEKI
            Constants.COFFEE_SHARE -> WidgetNameKey.COFFEE
            Constants.HEART_SHARE -> WidgetNameKey.HEART
            Constants.COCKTAIL_SHARE -> WidgetNameKey.COCKTAIL
            Constants.DUCK_SHARE -> WidgetNameKey.DUCK
            Constants.PIZZA_SHARE -> WidgetNameKey.PIZZA
            Constants.ZODIAC_SHARE -> WidgetNameKey.ZODIAC
            Constants.DONUT_SHARE -> WidgetNameKey.DONUT
            else -> "Unknown widget"
        }

        return Pair(name, option.second)
    }
}