package com.digicode.dodobattery.data.firebase
import com.google.firebase.firestore.DocumentId

data class UserApp(
    @DocumentId
    var id: String? = "",
    var pushToken: String = "",
    var lockedTemplates: Int = 0,
    var freeAvailableTemplates: Int = 0,
    var newConfirmationInstallation:Boolean = false,
    var language: String = "",
    var authId: String? = ""
)