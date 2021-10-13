package com.digicode.dodobattery.viewModel.main

import android.util.Log.e
import androidx.lifecycle.MutableLiveData
import com.digicode.dodobattery.Logger
import com.digicode.dodobattery.Purchaser
import com.digicode.dodobattery.data.model.GotFreeWidgetStatus
import com.digicode.dodobattery.data.model.Widget
import com.digicode.dodobattery.data.model.WidgetsFactory
import com.digicode.dodobattery.data.model.WidgetNameKey
import com.digicode.dodobattery.data.model.enums.LockedStatus
import com.digicode.dodobattery.data.model.mapper.WidgetStatusMapper
import com.digicode.dodobattery.data.preferences.PreferencesFacade
import com.digicode.dodobattery.data.repository.AppRepository
import com.digicode.dodobattery.firebase.firestore.FirestoreProvider
import com.digicode.dodobattery.viewModel.base.BaseViewModel
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val purchaser: Purchaser,
    private val firestoreProvider: FirestoreProvider,
    private val preferencesFacade: PreferencesFacade,
    private val logger: Logger
): BaseViewModel() {

    var selectedZodiac = 0
    private set;

    var selectedWidgetIndex = 0
    private set;

    private var widgetStateChangeListener: ((Int)->Unit)? = null

    private var widgets = MutableLiveData<List<Widget>>()
    private var isGotFreeWidget: MutableLiveData<Int> = MutableLiveData()
    private var freeAvalaibleWidgets: MutableLiveData<Int> = MutableLiveData()

    init {

        selectedWidgetIndex = preferencesFacade.selectedWidget.getValue()?.id ?: 0
        isGotFreeWidget.value = preferencesFacade.isGotFreeWidget.getValue()
        freeAvalaibleWidgets.value = preferencesFacade.freeAvalaibleWidgets.getValue()

        preferencesFacade.isGotFreeWidget.observable {
            isGotFreeWidget.postValue(it)
        }

        preferencesFacade.freeAvalaibleWidgets.observable {
            freeAvalaibleWidgets.postValue(it)
        }

        fetchWidgets()
        fetchRemoteConfig()

        purchaser.setOnInitializeListener {
            fetchWidgets()
            fetchRemoteConfig()
        }

        preferencesFacade.setOnWidgetStatusChangeListener { index, status ->
            widgets.value?.get(index)?.setLockStatus(WidgetStatusMapper().create(status))

            if(selectedWidgetIndex == index)
                preferencesFacade.selectedWidget.setValue(getSelectedWidget())

            widgetStateChangeListener?.invoke(index)
        }
    }

    fun setOnWidgetStatusChangeListener(listener: ((Int)->Unit)? = null){
        widgetStateChangeListener = listener
    }

    fun getWidgets() = toLiveData(widgets)
    fun getFreeAvalaibleWidgets() = toLiveData(freeAvalaibleWidgets)
    fun getIsGotFreeWidget() = toLiveData(isGotFreeWidget)

    fun updateIsGotFreeWidget(value : Int){
        isGotFreeWidget.value = value;
        preferencesFacade.isGotFreeWidget.setValue(value)
    }

    fun updateSelectedWidget(index: Int) {
        selectedWidgetIndex = index;
        preferencesFacade.selectedWidget.setValue(getSelectedWidget())
    }

    fun getPushToken() : String?{
        return firestoreProvider.userID
    }

    fun unlockSelectedWidget(
        completeListener: (() -> Unit)?,
        failedListener: ((Pair<String,String>) -> Unit)?
    ) {
        val widget = getSelectedWidget() ?: return

        if(freeAvalaibleWidgets.value!! > 0)
            appRepository.giftWidget(widget,completeListener)
        else
            appRepository.buyWidget(widget,completeListener,failedListener)
    }

    fun getShareLink(listener: ((String)->Unit)? = null) {
        firestoreProvider.createDynamicLink(listener)

        firestoreProvider.userID?.let {
            logger.log("DynamicLinkAndroidCreate", Pair("UserID", it))
        }
    }

    fun fetchRemoteConfig() {
        appRepository.fetchConfig().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val shareStatus = appRepository.fetchWidgetsShareOptions()
                val oldValues = widgets.value?.map {
                    Pair(it.name, it.isSharing)
                }
                val isDifferent = oldValues
                    ?.zip(shareStatus)
                    ?.firstOrNull { it.first.second != it.second.second }
                if (isDifferent != null) {
                    fetchWidgets()
                }
            } else {
                e("DoDo", "Cannot fetch remote config from server")
            }
        }
    }

    fun updateSelectedZodiac(selected: Int) {
        selectedZodiac = selected
        val widgetList = widgets.value
        widgetList?.map {
            if (it.name == WidgetNameKey.ZODIAC) {
                val zodiacList = it.subTypes?.second ?: intArrayOf()
                val subTypes = Pair(selected, zodiacList)
                it.subTypes = subTypes
            }
        }
        widgets.value = widgetList

        getSelectedWidget()?.let {
            updateWidget(it)
        }
    }

    private fun updateWidget(widget: Widget){
        preferencesFacade.selectedWidget.setValue(widget)
        preferencesFacade.setWidgetStatus(widget.id, widget.name, widget.lockedStatus.key)
    }

    private fun fetchWidgets() {
        val widgetList = WidgetsFactory.create()

        widgetList.forEach {
            val name = it.name
            var status = WidgetStatusMapper().create(preferencesFacade.getWidgetStatus(name))
            val sharing = preferencesFacade.getSharingStatus(name)

            if(status == LockedStatus.LOCKED){
               val isPurchased = purchaser.isPurchasedProduct(it.productID)
                if(isPurchased)
                    status = LockedStatus.PURCHASED
            }

            appRepository.updateLockedTemplates(false)

            it.setLockStatus(status)
            it.setSharing(sharing)
        }

        widgets.value = widgetList;
    }

    private fun getSelectedWidget() : Widget?{
        return widgets.value?.get(selectedWidgetIndex)
    }


}