package com.digicode.dodobattery.data.model.mapper

import com.digicode.dodobattery.data.model.enums.LockedStatus
import com.digicode.dodobattery.data.model.enums.LockedStatusKey
import javax.inject.Inject

class WidgetStatusMapper @Inject constructor() {

    fun create(statusKey: String?): LockedStatus {
        return when (statusKey) {
            LockedStatusKey.GIFT -> {
                LockedStatus.GIFT
            }
            LockedStatusKey.PURCHASED -> {
                LockedStatus.PURCHASED
            }
            else -> {
                LockedStatus.LOCKED
            }
        }
    }


}