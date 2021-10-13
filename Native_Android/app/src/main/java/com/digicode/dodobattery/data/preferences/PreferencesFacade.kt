package com.digicode.dodobattery.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.digicode.dodobattery.data.model.Widget
import com.digicode.dodobattery.data.model.enums.LockedStatus
import com.digicode.dodobattery.di.qualifier.AppContext
import com.digicode.dodobattery.domain.ReactiveProperty
import javax.inject.Inject


class PreferencesFacade @Inject constructor(
    @AppContext private val context: Context
){

    val isGotFreeWidget: ReactiveProperty<Int>
    val userID: ReactiveProperty<String?>
    val selectedWidget: ReactiveProperty<Widget?>
    val freeAvalaibleWidgets: ReactiveProperty<Int>
    val lockedWidgetsCount : ReactiveProperty<Int>

    private var widgetStateChangeListener: ((Int,String)->Unit)? = null

    val tutorialVideoLink: String?
        get() { return DEFAULT_VIDEO_LINK; }

    private val prefs: SharedPreferences

    init {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val json = prefs.getString(WIDGET_SELECTED_KEY,"")
        var widget = let {
            if(!json.isNullOrEmpty()){
                Widget.fromJson(json)
            }
            else{
                null
            }
        }

        isGotFreeWidget = ReactiveProperty(prefs.getInt(GOT_FREE_WIDGET, 0))
        userID = ReactiveProperty(prefs.getString(USER_ID_KEY, null))
        selectedWidget = ReactiveProperty(widget)
        freeAvalaibleWidgets = ReactiveProperty(prefs.getInt(FREE_WIDGETS_KEY, 0))
        lockedWidgetsCount = ReactiveProperty(prefs.getInt(LOCKED_WIDGETS_COUNT, 9))

        isGotFreeWidget.observable {
            it?.let { prefs.edit { putInt(GOT_FREE_WIDGET,it)} }
        }

        freeAvalaibleWidgets.observable {
            it?.let {  prefs.edit { putInt(FREE_WIDGETS_KEY, it) } }
        }

        userID.observable {
            prefs.edit { putString(USER_ID_KEY, it) }
        }

        lockedWidgetsCount.observable {
            it?.let { prefs.edit { putInt(LOCKED_WIDGETS_COUNT, it) } }
        }

        selectedWidget.observable {
            it?.let {
                val json = Widget.toJson(it)
                prefs.edit { putString(WIDGET_SELECTED_KEY, json) }
            }
        }
    }

    fun setOnWidgetStatusChangeListener(listener: ((Int,String)->Unit)? = null){
        widgetStateChangeListener = listener
    }

    fun setWidgetStatus(index: Int, widgetName: String, status: String) {
        val key = widgetName + STATUS_KEY
        prefs.edit { putString(key, status) }
        widgetStateChangeListener?.invoke(index,status)
    }

    fun getWidgetStatus(widgetName: String): String? {
        val key = widgetName + STATUS_KEY
        return prefs.getString(key, LockedStatus.PURCHASED.toString())
    }

    fun setSharingStatus(shareOption: String, sharing: Boolean) {
        prefs.edit { putBoolean(shareOption, sharing) }
    }

    fun getSharingStatus(widgetName: String): Boolean {
        val key = widgetName + SHARE_KEY
        return prefs.getBoolean(key, true)
    }


    companion object {
        private const val PREFS_NAME = "dodo_prefs"
        private const val USER_ID_KEY = "user_id"
        private const val FREE_WIDGETS_KEY = "free_widgets"
        private const val WIDGET_SELECTED_KEY = "widget_prefs"
        private const val FCM_TOKEN_KEY = "fcm_token"
        private const val LOCKED_WIDGETS_COUNT = "widgets_count"
        private const val GOT_FREE_WIDGET = "got_free_widget"
        private const val DEFAULT_VIDEO_LINK = "https://vimeo.com/505155294"
        private const val STATUS_KEY = "_status"
        private const val SHARE_KEY = "_share"
    }

}
