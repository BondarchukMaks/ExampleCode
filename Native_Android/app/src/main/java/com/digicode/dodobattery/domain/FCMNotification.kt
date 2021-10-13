package com.digicode.dodobattery.domain

import com.google.gson.annotations.SerializedName

data class FCMNotification(
    @SerializedName("title_loc_key")
    val titleKey:String,
    @SerializedName("body")
    val body:String,
    @SerializedName("body_loc_key")
    val bodyKey:String,
    @SerializedName("body_loc_args")
    val bodyArgs:Array<String>,
    val sound:String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FCMNotification

        if (titleKey != other.titleKey) return false
        if (body != other.body) return false
        if (bodyKey != other.bodyKey) return false
        if (!bodyArgs.contentEquals(other.bodyArgs)) return false
        if (sound != other.sound) return false

        return true
    }

    override fun hashCode(): Int {
        var result = titleKey.hashCode()
        result = 31 * result + body.hashCode()
        result = 31 * result + bodyKey.hashCode()
        result = 31 * result + bodyArgs.contentHashCode()
        result = 31 * result + sound.hashCode()
        return result
    }


}