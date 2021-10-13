package com.digicode.dodobattery.graphic

import android.content.Context
import android.graphics.drawable.Drawable
import com.digicode.dodobattery.R
import com.digicode.dodobattery.data.model.TemplateTypes
import com.digicode.dodobattery.data.model.Widget
import com.digicode.dodobattery.graphic.drawable.MaskHeart
import com.digicode.dodobattery.graphic.helpers.*

class ImageBuilder {

    private lateinit var context: Context
    private lateinit var widget: Widget
    private lateinit var batteryIndicators: Pair<Int, Boolean>
    private var showWatermark = false

    fun setContext(context: Context): ImageBuilder {
        this.context = context
        return this
    }

    fun setWidget(widget: Widget): ImageBuilder {
        this.widget = widget
        return this
    }

    fun setBatteryIndicators(indicators: Pair<Int, Boolean>): ImageBuilder {
        this.batteryIndicators = indicators
        return this
    }

    fun showWatermark(show: Boolean): ImageBuilder {
        this.showWatermark = show
        return this
    }

    fun build(): Drawable {

        //todo
        val maskHelper = when(widget.template.type) {
            TemplateTypes.MASK_A -> MaskAHelper()
            TemplateTypes.MASK_B -> MaskBHelper()
            TemplateTypes.MASK_C -> MaskCHelper()
            TemplateTypes.MASK_D -> MaskDHelper()
            TemplateTypes.MASK_DUCK -> MaskDuckHelper()
            TemplateTypes.MASK_MANEKI -> MaskManekiHelper()
            TemplateTypes.MASK_AQARIUM -> MaskAqaruiumHelper()
            TemplateTypes.MASK_COFFEE -> MaskCoffeeHelper()
            TemplateTypes.MASK_HEART -> MaskHeartHelper()
            TemplateTypes.MASK_COCKTAIL -> MaskCocktailHelper()
            TemplateTypes.IMAGES_SEQ -> ImageSeqHelper()
            else -> null
        }

        val drawable = maskHelper?.create(context, widget, batteryIndicators, showWatermark)
        val placeHolder = context.resources.getDrawable(R.drawable.ic_place_holder, context.theme)

        return drawable ?: placeHolder
    }
}