package com.digicode.dodobattery.firebase

import android.content.Intent
import android.net.Uri
import com.digicode.dodobattery.utils.Constants
import com.google.firebase.appinvite.FirebaseAppInvite
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase


class FBDynamicLinks {

    companion object{

        fun getDynamicLink(intent: Intent, listener: ((Uri?) -> Unit)? = null){

            Firebase.dynamicLinks
                .getDynamicLink(intent)
                .addOnSuccessListener { pendingDynamicLinkData ->

                    var deepLink: Uri? = null
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.link
                        listener?.invoke(deepLink)
                    }
                }.addOnFailureListener {
                    print("fail")
                }
        }

        fun createDynamicLink(userID: String,listener: ((String)->Unit)? = null){

            val uri = Uri.Builder()
                .scheme(Constants.SHARE_SCHEME)
                .authority(Constants.SHARE_AUTHORITY)
                .path(Constants.SHARE_USER_PATH)
                .appendQueryParameter(Constants.SHARE_USER_ID_PARAMETER,userID)
                .build()


            val dynamicLink = Firebase.dynamicLinks.shortLinkAsync {
                link = uri
                domainUriPrefix = Constants.SHARE_DOMAIN_URI
                androidParameters("com.digicode.dodobattery"){
                }
                iosParameters(Constants.SHARE_IOS_PARAMS) {
                    appStoreId = Constants.SHARE_IOS_APPSTORE_ID
                }
                buildShortDynamicLink()
            }

            dynamicLink.addOnSuccessListener {


                listener?.invoke(it.shortLink.toString())
            }.addOnFailureListener {
                print("error")
            }
        }
    }
}
