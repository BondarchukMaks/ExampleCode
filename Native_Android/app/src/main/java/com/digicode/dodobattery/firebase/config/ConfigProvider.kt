package com.digicode.dodobattery.firebase.config

import com.digicode.dodobattery.utils.Constants
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class ConfigProvider {

    private val remoteConfig = Firebase.remoteConfig
    private val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 6
    }

    init {
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

    fun fetchConfig() = remoteConfig.fetchAndActivate()

    fun fetchTutorialVideoLink(): String {
        return remoteConfig.getString(Constants.HOW_TO_WIDGET_VIDEO)
    }

    fun fetchWidgetsShareOptions(): List<Pair<String, Boolean>> {
        val aquarium = parseBoolean(Constants.AQUARIUM_SHARE)
        val maneki = parseBoolean(Constants.MANEKI_SHARE)
        val coffee = parseBoolean(Constants.COFFEE_SHARE)
        val heart = parseBoolean(Constants.HEART_SHARE)
        val cocktail = parseBoolean(Constants.COCKTAIL_SHARE)
        val duck = parseBoolean(Constants.DUCK_SHARE)
        val pizza = parseBoolean(Constants.PIZZA_SHARE)
        val zodiac = parseBoolean(Constants.ZODIAC_SHARE)
        val donut = parseBoolean(Constants.DONUT_SHARE)

        return listOf(aquarium, maneki, coffee, heart, cocktail, duck, pizza, zodiac, donut)
    }

    private fun parseBoolean(key: String): Pair<String, Boolean> {
        val initialValue = remoteConfig.getString(key)
        val value = initialValue != "false"
        return Pair(key, value)
    }
}