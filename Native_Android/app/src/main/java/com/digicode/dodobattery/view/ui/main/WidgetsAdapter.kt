package com.digicode.dodobattery.view.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.digicode.dodobattery.R
import com.digicode.dodobattery.data.model.CarouselItemTag
import com.digicode.dodobattery.data.model.Widget
import com.digicode.dodobattery.graphic.ImageBuilder
import com.digicode.dodobattery.utils.extensions.show
import kotlinx.android.synthetic.main.item_widget_list.view.*

class WidgetsAdapter(
    private val listener: OnWidgetClickListener,
    private val context:Context?
) : RecyclerView.Adapter<WidgetsAdapter.WidgetsItemViewHolder>() {


    var animAvalaible: Boolean = true;
    private var parentRecycler: RecyclerView? = null
    private var items: List<Widget> = listOf()
    private var batteryIndicators = Pair(100, false)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetsItemViewHolder =
        WidgetsItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_widget_list, parent, false)
        )

    override fun onBindViewHolder(holder: WidgetsItemViewHolder, position: Int) {
        val index = getItemIndexByPosition(position)
        holder.bind(items[index], position)

        if(animAvalaible)
            animateHolder(holder)
    }


    private fun animateHolder(holder: WidgetsItemViewHolder){
        var mEnlargeAnimation = AnimationUtils.loadAnimation(context, R.anim.enlarge);
        holder.itemView.startAnimation(mEnlargeAnimation)
    }


    override fun onViewRecycled(holder: WidgetsItemViewHolder) = holder.recycle()

    override fun getItemCount() = items.count()

    fun setItems(newItems: List<Widget>) {
        items = newItems
        notifyDataSetChanged()

        if(!isCachedItems)
            cacheImageItems(newItems)

    }

    private fun cacheImageItems(newItems: List<Widget>){

        context?.let { context ->
            val battery = if(batteryIndicators != null) batteryIndicators else Pair(100,false)

            newItems.forEach {
                ImageBuilder()
                    .setContext(context)
                    .setWidget(it)
                    .setBatteryIndicators(battery)
                    .build()
            }

            isCachedItems = true;
        }
    }


    fun setBatteryIndicators(indicators: Pair<Int, Boolean>) {
        batteryIndicators = indicators
        notifyDataSetChanged()
    }

    fun findItemById(id: Int): Widget? {
        return items.firstOrNull { it.id == id }
    }

    private fun getItemIndexByPosition(position: Int) = position % items.size

    inner class WidgetsItemViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: Widget, position: Int) {
            view.root.tag = CarouselItemTag(id = item.id, position = position)

            val widgetImage = ImageBuilder()
                .setContext(view.context)
                .setWidget(item)
                .setBatteryIndicators(batteryIndicators)
                .build()

            view.widgetImage.setImageDrawable(widgetImage)

            view.widgetImage.setOnClickListener {
                parentRecycler?.smoothScrollToPosition(adapterPosition)
                listener.onWidgetClick(item)
            }

            view.expandView.setOnClickListener {
                listener.onExpandButtonClick(item.subTypes?.second, item.name)
            }

            view.expandView.show(item.subTypes != null)
        }

        fun recycle() {
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        parentRecycler = recyclerView
    }

    companion object
    {
        private var isCachedItems: Boolean = false;
    }
}
