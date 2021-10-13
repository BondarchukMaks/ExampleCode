package com.digicode.dodobattery.view.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.digicode.dodobattery.R
import com.digicode.dodobattery.data.model.Widget
import com.digicode.dodobattery.data.model.enums.LockedStatus
import com.digicode.dodobattery.utils.extensions.show
import kotlinx.android.synthetic.main.layout_header.view.*
import kotlinx.android.synthetic.main.layout_status.view.*

class StatusLayout : LinearLayout {

    constructor(
        context: Context
    ) : this(context, null)

    constructor(
        context: Context,
        attributes: AttributeSet?
    ) : this(context, attributes, 0)

    constructor(
        context: Context,
        attributes:
        AttributeSet?,
        defStyle: Int
    ) : super(context, attributes, defStyle) {

        inflateLayout()

        attributes?.let {
            val typedArray = context.obtainStyledAttributes(
                it, R.styleable.StatusLayout, 0, 0
            )

            typedArray.recycle()
        }
    }

    fun update(widget: Widget, freeWidgetsCount : Int) {
        updateArrowUpView(widget.lockedStatus)
        updateUnlockText(widget.lockedStatus, widget.isSharing,freeWidgetsCount)
        updateShareButton(widget.lockedStatus, widget.isSharing,freeWidgetsCount)
        updateBuyButton(widget.lockedStatus,freeWidgetsCount)
    }

    private fun updateArrowUpView(status: LockedStatus) {
        val resImageId = if (status == LockedStatus.LOCKED) {
            R.drawable.ic_arrow_up_white
        } else {
            R.drawable.ic_arrow_up_white
        }
        arrowImageView.setImageResource(resImageId)
    }

    private val animSpeed : Long = 700;

    private fun updateUnlockText(status: LockedStatus, sharing: Boolean,freeWidgetsCount: Int) {
        val textResId = when {

            status == LockedStatus.PURCHASED || status == LockedStatus.GIFT -> {
                R.string.unlocked
            }
            freeWidgetsCount > 0 ->{
                R.string.unlock_widget
            }
            status == LockedStatus.LOCKED && sharing -> {
                R.string.unlock_with_options
            }
            else -> {
                R.string.unlock_with_options
            }
        }

        val text = resources.getString(textResId)

        unlockTextView.text = text;

        if(unlockTextView.alpha == 0F)
            unlockTextView.animate().alpha(1F).setDuration(animSpeed).setListener(null)

    }

    private fun getTargetAlpha(value: Boolean): Float {
         if(value) return 1F else return 0F
    }

    private fun updateBuyButton(status: LockedStatus, freeWidgetsCount: Int) {

        val textID = if(freeWidgetsCount == 0) R.string.buy_for_dollar else R.string.unlock_free
        buyButton.text = resources.getString(textID)
        countWidgetsTextView.text = freeWidgetsCount.toString()
        countWidgetsTextView.alpha = getTargetAlpha(freeWidgetsCount > 0 && status == LockedStatus.LOCKED)
        buyButton.alpha = getTargetAlpha(status == LockedStatus.LOCKED)
    }

    private fun updateShareButton(status: LockedStatus, sharing: Boolean,freeWidgetsCount: Int) {

        var isShow = status == LockedStatus.LOCKED && sharing
        if(freeWidgetsCount > 0)
            isShow = false;

        shareButton.alpha = getTargetAlpha(isShow)
        orView.alpha = getTargetAlpha(isShow)
    }

    private fun inflateLayout() {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.layout_status, this, true)
    }

    fun setBuyButtonClickListener(listener: (v: View) -> Unit) {
        buyButton.setOnClickListener {
            listener(this)
        }
    }

    fun setShareButtonClickListener(listener: (v: View) -> Unit) {
        shareButton.setOnClickListener {
            listener(this)
        }
    }
}