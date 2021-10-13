package com.digicode.dodobattery.viewModel.menu

import com.digicode.dodobattery.data.repository.AppRepository
import com.digicode.dodobattery.firebase.firestore.FirestoreProvider
import com.digicode.dodobattery.viewModel.base.BaseViewModel
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val firestoreProvider: FirestoreProvider
): BaseViewModel() {

    fun getShareLink(listener: ((String)->Unit)? = null)  {
        firestoreProvider.createDynamicLink{
            listener?.invoke(it)
        }
    }

}