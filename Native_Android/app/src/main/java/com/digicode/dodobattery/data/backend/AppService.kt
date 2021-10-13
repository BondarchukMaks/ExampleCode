package com.digicode.dodobattery.data.backend
import com.digicode.dodobattery.domain.FCMRequest
import com.digicode.dodobattery.domain.FCMResponse
import retrofit2.http.*
import io.reactivex.rxjava3.core.Single
import retrofit2.Call

interface AppService {

    @Headers(
        "Content-type: application/json",
        "Authorization: key=AAAAzJ0thvc:APA91bGqtOVq0byvGhiLnQw01TSzjmavUZX9XPH_hsjozi1Qh7yrn9fr6OHE3uu1b4E8ZsluXqHiP7F1eTw2UaVYdXv6jD8kRws0svft7ZKgY5BZZeEwttN5pxel26oRvi3GCxOqlz8i"
    )
    @POST("/fcm/send")
    fun sendLocalizedPushNotification(@Body request : FCMRequest): Call<FCMResponse>
}




