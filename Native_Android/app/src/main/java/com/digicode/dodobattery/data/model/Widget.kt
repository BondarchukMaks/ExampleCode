package com.digicode.dodobattery.data.model

import com.digicode.dodobattery.data.model.enums.LockedStatus
import com.google.gson.Gson

data class Widget(
    val id: Int,
    val name: String,
    val productID: String,
    var lockedStatus: LockedStatus,
    var isSharing: Boolean,
    val template: Template,
    var subTypes: Pair<Int, IntArray>?
)  {

    fun setLockStatus(lockedStatus: LockedStatus): Widget {
        this.lockedStatus = lockedStatus
        return this
    }

    fun setSharing(isSharing: Boolean): Widget {
        this.isSharing = isSharing
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Widget

        if (id != other.id) return false
        if (name != other.name) return false
        if (lockedStatus != other.lockedStatus) return false
        if (isSharing != other.isSharing) return false
        if (template != other.template) return false
        if (subTypes != other.subTypes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + lockedStatus.hashCode()
        result = 31 * result + isSharing.hashCode()
        result = 31 * result + template.hashCode()
        result = 31 * result + (subTypes?.hashCode() ?: 0)
        return result
    }

    companion object{

        fun toJson(widget: Widget) :String{

            val gson = Gson()
            val json = gson.toJson(widget)
            return json
        }

        fun fromJson(json: String) :Widget{
            val gson = Gson()
            return gson.fromJson(json, Widget::class.java)
        }
    }

}