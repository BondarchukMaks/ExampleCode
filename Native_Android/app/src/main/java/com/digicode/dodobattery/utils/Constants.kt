package com.digicode.dodobattery.utils

class Constants {

    companion object {

        const val WIDGET_UPDATE : String = "widget_update"


        /* VALUES */
        const val EMPTY_INT_VAL = -1
        const val ZODIAC_ITEMS = "zodiac_items"
        const val SELECTED_ITEM = "selected_item"
        const val IS_NEW_USER = "is_new_user"


        //Firestore
        const val USERS_KEY = "users"
        const val SAMSUNG_NAME = "samsung"


        //Firestore RemoteConfig
        const val HOW_TO_WIDGET_VIDEO = "android_how_to_add_widget_video_link"

        const val AQUARIUM_SHARE = "aquarium_share"
        const val MANEKI_SHARE = "maneki_share"
        const val COFFEE_SHARE = "coffee_share"
        const val HEART_SHARE = "heart_share"
        const val COCKTAIL_SHARE = "cocktail_share"
        const val DUCK_SHARE = "duck_share"
        const val PIZZA_SHARE = "pizza_share"
        const val ZODIAC_SHARE = "zodiac_share"
        const val DONUT_SHARE = "donut_share"

        //ShareLink
        const val SHARE_SCHEME  = "https"
        const val SHARE_AUTHORITY  = "www.coolbattery.com"
        const val SHARE_USER_PATH = "users"
        const val SHARE_USER_ID_PARAMETER= "userID"
        const val SHARE_DOMAIN_URI = "https://coolbattery.page.link"
        const val SHARE_IOS_PARAMS = "com.digicode.CoolBattery"
        const val SHARE_IOS_APPSTORE_ID = "1529404164"


        const val RECEIVE_ACTION = "com.google.android.c2dm.intent.RECEIVE"

        //Bitmaps
        const val LOCK_BITMAP = "lock"
        const val PERCENTS_BITMAP = "percent+"
        const val CHARGING_BITMAP = "charging"
        const val DIGIT_BITMAP = "digit+"

    }
}