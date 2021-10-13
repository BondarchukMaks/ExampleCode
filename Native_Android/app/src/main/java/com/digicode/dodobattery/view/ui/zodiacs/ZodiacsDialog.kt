package com.digicode.dodobattery.view.ui.zodiacs

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.digicode.dodobattery.R
import com.digicode.dodobattery.utils.Constants
import com.digicode.dodobattery.view.ui.base.fragment.BaseDialog
import kotlinx.android.synthetic.main.dialog_zodiacs.*

class ZodiacsDialog : BaseDialog() {

    private lateinit var items: IntArray

    private var selected: Int = -1

    override fun getLayoutRes() = R.layout.dialog_zodiacs

    override fun initView() {
        zodiacsRecView.layoutManager = LinearLayoutManager(requireContext())
        zodiacsRecView.adapter = createAdapter()
    }

    override fun parseParameters(bundle: Bundle) {
        super.parseParameters(bundle)
        items = bundle.getIntArray(Constants.ZODIAC_ITEMS) ?: intArrayOf()
        selected = bundle.getInt(Constants.SELECTED_ITEM)
    }

    override fun setWindowHeight(): Int {
        return (resources.displayMetrics.heightPixels * 0.7).toInt()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntArray(Constants.ZODIAC_ITEMS, items)
        outState.putInt(Constants.SELECTED_ITEM, selected)
    }

    private fun createAdapter(): ZodiacsAdapter {
        return ZodiacsAdapter(items, selected) {
            getOnClickListener()?.onZodiacClick(it)
            dismiss()
        }
    }

    private fun getOnClickListener(): OnZodiacItemClickListener? {
        val parentFragment = requireParentFragment()
        return if (parentFragment is OnZodiacItemClickListener) parentFragment else null
    }

    companion object {
        fun newInstance(items: IntArray?, selected: Int): ZodiacsDialog {
            val arguments = Bundle()
            arguments.putIntArray(Constants.ZODIAC_ITEMS, items)
            arguments.putInt(Constants.SELECTED_ITEM, selected)

            val fragment = ZodiacsDialog()
            fragment.arguments = arguments
            return fragment
        }
    }

    interface OnZodiacItemClickListener {

        fun onZodiacClick(selected: Int)
    }

}