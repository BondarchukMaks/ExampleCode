package com.digicode.dodobattery.domain


data class FCMRequest(val to:String, val notification: FCMNotification, val data: FCMCustomData)