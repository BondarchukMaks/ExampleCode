package com.digicode.dodobattery.view.ui.dialog

import com.digicode.dodobattery.R
import com.digicode.dodobattery.utils.extensions.setUnderline
import com.digicode.dodobattery.view.ui.base.fragment.BaseDialog
import com.google.android.play.core.review.ReviewManagerFactory
import kotlinx.android.synthetic.main.dialog_rate_us.*

interface IRateUs{
    fun askRatings()
}

class RateUsDialog : BaseDialog() , IRateUs {

    override fun getLayoutRes() = R.layout.dialog_rate_us

    override fun initView() {
        cancelButton.setUnderline()
        initListeners()

    }

    private fun initListeners() {
        confirmButton.setOnClickListener {
            askRatings()
        }
        cancelButton.setOnClickListener {
            dismiss()
        }
    }



    // works only in Internal testing
    override fun askRatings() {

        context?.let {
            val manager = ReviewManagerFactory.create(it)
            manager.requestReviewFlow().addOnCompleteListener { request ->
                if (request.isSuccessful) {
                    val reviewInfo = request.result
                    activity?.let {
                        manager.launchReviewFlow(it, reviewInfo).addOnFailureListener {
                            print("failureRating")
                            dismiss()
                        }.addOnCompleteListener { _ ->
                            print("complete")
                            dismiss()
                        }
                    }

                } else {
                    print("error")
                }
            }
        }
    }

    companion object {
        fun newInstance(): RateUsDialog {
            return RateUsDialog()
        }
    }
}