package com.digicode.dodobattery.data.repository

import android.content.Intent
import androidx.core.math.MathUtils
import com.digicode.dodobattery.Logger
import com.digicode.dodobattery.Purchaser
import com.digicode.dodobattery.data.backend.AppService
import com.digicode.dodobattery.data.model.Widget
import com.digicode.dodobattery.data.model.enums.LockedStatus
import com.digicode.dodobattery.data.model.mapper.WidgetShareMapper
import com.digicode.dodobattery.data.preferences.PreferencesFacade
import com.digicode.dodobattery.domain.*
import com.digicode.dodobattery.firebase.FBDynamicLinks
import com.digicode.dodobattery.firebase.config.ConfigProvider
import com.digicode.dodobattery.firebase.firestore.FirestoreProvider
import com.google.android.gms.tasks.Task
import retrofit2.Response
import javax.inject.Inject


class AppRepository @Inject constructor(
    private val preferencesFacade: PreferencesFacade,
    private val firestoreProvider: FirestoreProvider,
    private var appService: AppService,
    private val remoteConfig: ConfigProvider,
    private val purchaser: Purchaser,
    private val logger: Logger
) {

    fun giftWidget(widget: Widget, completeListener: (() -> Unit)?){

        if(widget.lockedStatus != LockedStatus.LOCKED)
            return

        widget.setLockStatus(LockedStatus.GIFT)
        val targetCount = preferencesFacade.freeAvalaibleWidgets.getValue() -1;
        preferencesFacade.freeAvalaibleWidgets.setValue(targetCount)
        preferencesFacade.setWidgetStatus(widget.id, widget.name,LockedStatus.GIFT.key)
        preferencesFacade.selectedWidget.setValue(widget)

        var lockedWidgets = preferencesFacade.lockedWidgetsCount.getValue() - 1;
        lockedWidgets = MathUtils.clamp(lockedWidgets,0,9)
        preferencesFacade.lockedWidgetsCount.setValue(lockedWidgets)

        updateLockedTemplates(true)
        completeListener?.invoke()
    }

    fun buyWidget (widget: Widget, completeListener: (() -> Unit)?, failedListener: ((Pair<String,String>) -> Unit)?)
    {
        if(widget.lockedStatus != LockedStatus.LOCKED)
            return

        purchaser.buyProduct(widget.productID, {

            widget.setLockStatus(LockedStatus.PURCHASED)

            var lockedWidgets = preferencesFacade.lockedWidgetsCount.getValue() - 1;
            lockedWidgets = MathUtils.clamp(lockedWidgets,0,9)
            preferencesFacade.lockedWidgetsCount.setValue(lockedWidgets)

            preferencesFacade.selectedWidget.setValue(widget)
            preferencesFacade.setWidgetStatus(widget.id, widget.name,LockedStatus.PURCHASED.key)
            updateLockedTemplates(false)
            completeListener?.invoke()
        }, {
            failedListener?.invoke(it)
        })
    }

    fun checkIfInstallationFromDeepLink(intent: Intent){

        firestoreProvider.setOnAuthSucessListener {
            FBDynamicLinks.getDynamicLink(intent){ uri ->

                logger.log("DynamicLinkAndroidGetURL",Pair("URL",uri.toString()))
                logger.log("DynamicLinkAndroidGetDynamicLink",Pair("URL",uri.toString()))

                //todo
                var short = uri.toString()
                short =short.replace("https://www.coolbattery.com/users?userID=" , "")
                short =short.replace("https://coolbattery.com/users?userID=" , "")

                firestoreProvider.getUserFromDynamicLink(short){ user ->
                    logger.log("DynamicLinkAndroidParse",Pair("UserId",user.id))

                    if(user.freeAvailableTemplates < user.lockedTemplates){
                        user.freeAvailableTemplates +=1;
                        user.newConfirmationInstallation = true;
                        firestoreProvider.updateUser(user)
                        sendLocalizedPushNotification(user.pushToken)
                    }
                }
            }
        }
    }

    fun getTutorialVideoLink(): String? {
        return preferencesFacade.tutorialVideoLink;
    }

    fun fetchConfig(): Task<Boolean> {
        return remoteConfig.fetchConfig()
    }

    fun fetchWidgetsShareOptions(): List<Pair<String, Boolean>> {
        val shareOptions = remoteConfig.fetchWidgetsShareOptions()
        shareOptions.forEach {
            preferencesFacade.setSharingStatus(it.first, it.second)
        }
        return shareOptions.map {
            WidgetShareMapper().convertOptionToName(it)
        }
    }

    fun getTotalWidgetsCount() : Int{
        return remoteConfig.fetchWidgetsShareOptions().count()
    }

    fun getPurchasedWidgetsCount() : Int {
        var count = 0;
        val shareOptions = remoteConfig.fetchWidgetsShareOptions()
        shareOptions.forEach {
            if(it.second){
                count++;
            }
        }
        return count
    }

    fun updateLockedTemplates(updateFreeTemplates: Boolean){

        if(updateFreeTemplates){
            val freeAvalaibleMap = hashMapOf("freeAvailableTemplates" to firestoreProvider.userApp.freeAvailableTemplates - 1)
            firestoreProvider.updateField(freeAvalaibleMap)
        }

        val lockedTemplates = preferencesFacade.lockedWidgetsCount.getValue()
        val lockedMap = hashMapOf("lockedTemplates" to lockedTemplates)
        firestoreProvider.updateField(lockedMap)

        if(firestoreProvider.userApp.freeAvailableTemplates > lockedTemplates){
            val freeAvalableMap = hashMapOf("freeAvailableTemplates" to lockedTemplates)
            firestoreProvider.updateField(freeAvalableMap)
        }
    }

    fun sendLocalizedPushNotification(token:String){

        val notification = FCMNotification("", "Congratulations! Someone installed the app", "Congratulations! Someone installed the app",arrayOf(), "default")
        val customData = FCMCustomData("test_id")
        val request = FCMRequest(token, notification, customData)
        val call = appService.sendLocalizedPushNotification(request)

        call.enqueue(object : retrofit2.Callback<FCMResponse> {

            override fun onResponse(
                call: retrofit2.Call<FCMResponse>,
                response: Response<FCMResponse>
            ) {
                print(response.body()?.success)
            }

            override fun onFailure(call: retrofit2.Call<FCMResponse>, t: Throwable) {
                print("fail")
            }
        })
    }
}

