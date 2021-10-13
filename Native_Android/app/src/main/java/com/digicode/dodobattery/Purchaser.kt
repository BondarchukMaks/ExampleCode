package com.digicode.dodobattery

import android.app.Activity
import android.widget.Toast
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.TransactionDetails
import com.google.api.Billing


class Purchaser(
) : BillingProcessor.IBillingHandler
{

    private val LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlZq7G0X/4DIR7XAflV/wYuHpPe79JWAT3pU69D7f8AK7LA1rcMxSBPCA0EmeR6itNjsdUjhKDHb5QEcyiu5vV9G0JS/mNwpr6csclYKF8e1EXwUKbRZOP74RbL8lAGL3Wc4z63oKzdQsdOMUpdDDhSSo4FjiVbW29HfZDXbAUZCMi6kXhmEictn0rGJnK4Dq3DTL3WA+IE+wyCYAKLPC/6ZnpHETZu9isgdAacpIUCEgNwmhXyJTpArLvg0QorsD9IOoyWZP6vPqO1YQdTOGh6wbVBY31t7MtDKiYujGsNojCPFlEboZ+DViIjGVuHfURaKwWvCfzk9pV9Z2PgR2iwIDAQAB"

    var purchaseInProgress :Boolean = false
        private set

    var productIdInProgress: String = ""
        private set

    var isInitialized : Boolean = false
        private set

    private lateinit var activity : Activity
    private var initializeListener : (() -> Unit)? = null
    private var purchaseFailedListener : ((Pair<String, String>) -> Unit)? = null
    private var purchaseCompleteListener : ((String)->Unit)? = null

    lateinit var billingProcessor: BillingProcessor
    private set

    fun initialize(activityMain: Activity){
        activity = activityMain
        billingProcessor = BillingProcessor(activity, LICENSE_KEY, this)
    }

    fun release(){
        billingProcessor.release()
    }

    fun buyProduct(
        productId: String,
        completeListener: ((String) -> Unit)?,
        failedListener: ((Pair<String, String>) -> Unit)?
    ) {
        if(!isInitialized){
            failedListener?.invoke(Pair(productId, "in-app not avalaible"))
            return
        }

        purchaseCompleteListener = completeListener
        purchaseFailedListener = failedListener
        purchaseInProgress = true;
        productIdInProgress = productId
        billingProcessor.purchase(activity, productId)
    }

    override fun onBillingInitialized() {
        isInitialized = true
        val v = billingProcessor.loadOwnedPurchasesFromGoogle()
        initializeListener?.invoke()
    }


    override fun onProductPurchased(productId: String, details: TransactionDetails?) {
        purchaseCompleteListener?.invoke(productId)
        purchaseInProgress = false
        productIdInProgress = ""
    }

    override fun onBillingError(errorCode: Int, error: Throwable?) {
        purchaseFailedListener?.invoke(Pair(productIdInProgress, errorCode.toString()))
        purchaseInProgress = false
        productIdInProgress = ""
    }

    fun setOnInitializeListener(listener : (() -> Unit)){
        initializeListener = listener
    }

    override fun onPurchaseHistoryRestored() {
    }

    fun isPurchasedProduct(productId: String) : Boolean{
        return billingProcessor.isPurchased(productId)
    }
}