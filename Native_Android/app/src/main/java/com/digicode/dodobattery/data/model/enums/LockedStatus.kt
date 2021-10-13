package com.digicode.dodobattery.data.model.enums

enum class LockedStatus(val key: String) {
    LOCKED(LockedStatusKey.LOCKED),
    GIFT(LockedStatusKey.GIFT),
    PURCHASED(LockedStatusKey.PURCHASED)
}

class LockedStatusKey {
    companion object {
        const val LOCKED = "locked"
        const val GIFT = "gift"
        const val PURCHASED = "purchased"
    }
}