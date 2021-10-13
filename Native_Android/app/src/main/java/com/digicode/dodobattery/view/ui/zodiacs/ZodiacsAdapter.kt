package com.digicode.dodobattery.view.ui.zodiacs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digicode.dodobattery.R
import com.digicode.dodobattery.utils.extensions.show
import com.digicode.dodobattery.utils.getColor
import kotlinx.android.synthetic.main.item_zodiac_list.view.*

class ZodiacsAdapter(
    private val items: IntArray,
    private var selected: Int,
    private val listener: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_zodiac_list, parent, false)
        return ZodiacHolder(view) {
            selected = it
            notifyDataSetChanged()
            listener.invoke(it)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ZodiacHolder) {
            holder.bind(items[position], position, selected == position)
        }
    }

    inner class ZodiacHolder(
        item: View,
        private val listener: (Int) -> Unit
    ): RecyclerView.ViewHolder(item) {

        fun bind(value: Int, position: Int, selected: Boolean) {

            itemView.nameTextView.setText(value)

            val textColor = if (selected) R.color.colorAccent else R.color.dark_gray
            itemView.nameTextView.setTextColor(getColor(itemView.context, textColor))

            itemView.checkImage.show(selected)

            itemView.setOnClickListener {
                listener.invoke(position)
            }

        }

    }

}