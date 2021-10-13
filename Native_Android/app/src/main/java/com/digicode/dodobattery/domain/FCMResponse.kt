package com.digicode.dodobattery.domain

import com.google.gson.annotations.SerializedName

data class FCMResponse(
    @SerializedName("multicast_id")
    val multicastID:String,
    val success: Int,
    val failure : Int,
    @SerializedName("canonical_ids")
    val canonicalIDS: Int);