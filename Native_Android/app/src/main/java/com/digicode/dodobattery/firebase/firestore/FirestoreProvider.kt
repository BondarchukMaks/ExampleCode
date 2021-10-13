package com.digicode.dodobattery.firebase.firestore

import androidx.lifecycle.MutableLiveData
import com.digicode.dodobattery.data.firebase.UserApp
import com.digicode.dodobattery.data.preferences.PreferencesFacade
import com.digicode.dodobattery.firebase.FBDynamicLinks
import com.digicode.dodobattery.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class FirestoreProvider  @Inject constructor(
    private val preferencesFacade: PreferencesFacade
) : FirebaseAuth.AuthStateListener
{
    var userID:String? = null;
    private var userListener : ((String)->Unit)? = null

    var fcmToken: String = ""
    private set;

    var userApp:UserApp
    private set;


    private var curUser = MutableLiveData<FirebaseUser>()
    private val auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore

    private var authListener : (()->Unit)? = null

    init {
        auth.addAuthStateListener(this)

        val id = preferencesFacade.userID.getValue()
        userApp = UserApp("","",9,
            0,false, Locale.getDefault().language,
            curUser.value?.uid)

        id?.let {
            userID = it;
            userListener?.invoke(it)
        }


        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            fcmToken = it;

            auth.signInAnonymously().addOnSuccessListener { authResult ->
                authResult.user?.let { firebaseUser ->

                    authListener?.invoke()
                    curUser.value = firebaseUser;
                    fetchData()
                }
            }.addOnFailureListener {

            }
        }.
        addOnFailureListener {
            fcmToken = ""
        }
    }

    fun setOnAuthSucessListener (listener : (()->Unit)? = null){
        authListener = listener;
        auth.currentUser?.let {
            authListener?.invoke()
        }
    }

    override fun onAuthStateChanged(state: FirebaseAuth) {
    }

    fun createNewUser(){
        val newUserApp = UserApp("", fcmToken,preferencesFacade.lockedWidgetsCount.getValue() ?:9,0,
            false,Locale.getDefault().language,curUser.value?.uid)
        addUser(newUserApp)
    }

    fun createDynamicLink(listener: ((String)->Unit)? = null) {

        getUserId { userID ->

            FBDynamicLinks.createDynamicLink(userID){
                val shareText =
                    "Hi, I found a cool widget app $it Check it out!"
                listener?.invoke(shareText)
            }
        }
        
    }

    fun getUserId(listener: ((String)->Unit)? = null) {

        if(userID == null)
            userListener = listener;
        else{
            listener?.invoke(userID!!)
        }
    }

    fun fetchData(){

        if(userID == null){
            createNewUser()
            return
        }

        val document = db.collection(Constants.USERS_KEY).document(userID as String)
        document.addSnapshotListener { value, error ->
            val newUserApp = value?.toObject(UserApp::class.java)
            newUserApp?.let {
                userApp = newUserApp
                val d = userApp.pushToken
            }
        }
    }

    fun addUser(user: UserApp) {

        db.collection(Constants.USERS_KEY).add(user).addOnSuccessListener {
            userID = it.id;
            preferencesFacade.userID.setValue(userID)
            userListener?.invoke(it.id)
            fetchData()
            print("success")
        }.addOnFailureListener {
            print("fail")
        }
    }

    fun updateUser(user: UserApp)
    {
        user.id?.let {
            getDB().document(it).set(user)
        } ?: kotlin.run {
            print("user id is null")
        }
    }

    fun <T,T1> updateField(field :HashMap<T,T1>){

        userID?.let {
            getDB().document(it).set(field, SetOptions.merge()).addOnFailureListener {
                print("error")
            }
        }
    }

    fun getUserFromDynamicLink(userID: String, listener: ((UserApp) -> Unit)? = null){

        val docRef = getDB().document(userID)

        docRef.get().addOnSuccessListener { value ->
            val user = value?.toObject(UserApp::class.java)

            user?.let {
                listener?.invoke(user)
            }
        }.
        addOnFailureListener {
            print("fail")
        }
    }

    private fun getDB() : CollectionReference{
        return db.collection(Constants.USERS_KEY)
    }
}




