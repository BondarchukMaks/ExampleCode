package com.digicode.dodobattery.data.model

import com.digicode.dodobattery.R
import com.digicode.dodobattery.data.model.enums.LockedStatus
import com.digicode.dodobattery.data.model.enums.ZodiacTypesMapper

class WidgetsFactory {

    private val list = mutableListOf<Widget>()

    fun getWidgetList() = list.toList()

    private fun createAquarium() : Widget{
        val template = Template(
            WidgetNameKey.AQUARIUM,
            TemplateTypes.MASK_AQARIUM,
            intArrayOf(R.drawable.aquarium_main_1, R.drawable.aquarium_main_2, R.drawable.aquarium_main_3),
            intArrayOf(R.drawable.aquarium_mask),
            intArrayOf(R.drawable.aquarium_cover),
            intArrayOf(
                R.drawable.aquarium_0,
                R.drawable.aquarium_1,
                R.drawable.aquarium_2,
                R.drawable.aquarium_3,
                R.drawable.aquarium_4,
                R.drawable.aquarium_5,
                R.drawable.aquarium_6,
                R.drawable.aquarium_7,
                R.drawable.aquarium_8,
                R.drawable.aquarium_9),
            R.drawable.aquarium_percents)

        val widget = Widget(
            0,
            WidgetNameKey.AQUARIUM,
            WidgetNameKey.AQUARIUM,
            LockedStatus.LOCKED,
            true,
            template,
            null
        )

        return widget
    }

    private fun addAqarium() {
        val template = Template(
            WidgetNameKey.AQUARIUM,
            TemplateTypes.MASK_AQARIUM,
            intArrayOf(R.drawable.aquarium_main_1, R.drawable.aquarium_main_2, R.drawable.aquarium_main_3),
            intArrayOf(R.drawable.aquarium_mask),
            intArrayOf(R.drawable.aquarium_cover),
            intArrayOf(
                R.drawable.aquarium_0,
                R.drawable.aquarium_1,
                R.drawable.aquarium_2,
                R.drawable.aquarium_3,
                R.drawable.aquarium_4,
                R.drawable.aquarium_5,
                R.drawable.aquarium_6,
                R.drawable.aquarium_7,
                R.drawable.aquarium_8,
                R.drawable.aquarium_9),
            R.drawable.aquarium_percents)




        val widget = Widget(
            0,
            WidgetNameKey.AQUARIUM,
            WidgetNameKey.AQUARIUM,
            LockedStatus.LOCKED,
            true,
            template,
            null
        )

        list.add(widget)
    }

    private fun addManeki() {
        val template = Template(
            WidgetNameKey.MANEKI,
            TemplateTypes.MASK_MANEKI,
            intArrayOf(R.drawable.maneki_full, R.drawable.maneki_empty, R.drawable.maneki_level),
            intArrayOf(R.drawable.maneki_level_mask, R.drawable.maneki_full_mask),
            null,
            intArrayOf(
                R.drawable.maneki_0,
                R.drawable.maneki_1,
                R.drawable.maneki_2,
                R.drawable.maneki_3,
                R.drawable.maneki_4,
                R.drawable.maneki_5,
                R.drawable.maneki_6,
                R.drawable.maneki_7,
                R.drawable.maneki_8,
                R.drawable.maneki_9),
            R.drawable.maneki_prcnt)

        val widget = Widget(
            1,
            WidgetNameKey.MANEKI,
            WidgetNameKey.MANEKI,
            LockedStatus.LOCKED,
            true, template,
            null
        )

        list.add(widget)
    }

    private fun addCoffee() {
        val template = Template(
            WidgetNameKey.COFFEE,
            TemplateTypes.MASK_COFFEE,
            intArrayOf(R.drawable.coffee_main_1, R.drawable.coffee_main_2, R.drawable.coffee_main_3),
            intArrayOf(R.drawable.coffee_mask),
            intArrayOf(R.drawable.coffee_cover),
            intArrayOf(
                R.drawable.coffee_0,
                R.drawable.coffee_1,
                R.drawable.coffee_2,
                R.drawable.coffee_3,
                R.drawable.coffee_4,
                R.drawable.coffee_5,
                R.drawable.coffee_6,
                R.drawable.coffee_7,
                R.drawable.coffee_8,
                R.drawable.coffee_9),
            R.drawable.coffee_percents)

        val widget = Widget(
            2,
            WidgetNameKey.COFFEE,
            WidgetNameKey.COFFEE,
            LockedStatus.LOCKED,
            true,
            template,
            null
        )

        list.add(widget)
    }

    private fun addHeart() {
        val template = Template(
            WidgetNameKey.HEART,
            TemplateTypes.MASK_HEART,
            intArrayOf(R.drawable.heart_full, R.drawable.heart_empty, R.drawable.heart_level),
            intArrayOf(R.drawable.heart_level_mask, R.drawable.heart_full_mask),
            null,
            intArrayOf(
                R.drawable.heart_0,
                R.drawable.heart_1,
                R.drawable.heart_2,
                R.drawable.heart_3,
                R.drawable.heart_4,
                R.drawable.heart_5,
                R.drawable.heart_6,
                R.drawable.heart_7,
                R.drawable.heart_8,
                R.drawable.heart_9),
            R.drawable.heart_prcnt)

        val widget = Widget(
            3,
            WidgetNameKey.HEART,
            WidgetNameKey.HEART,
            LockedStatus.LOCKED,
            true,
            template,
            null
        )

        list.add(widget)
    }

    private fun addCocktail() {
        val template = Template(
            WidgetNameKey.COCKTAIL,
            TemplateTypes.MASK_COCKTAIL,
            intArrayOf(R.drawable.cocktail_main_1, R.drawable.cocktail_tube),
            intArrayOf(R.drawable.cocktail_mask, R.drawable.cocktail_mask_2),
            intArrayOf(R.drawable.cocktail_cover),
            intArrayOf(
                R.drawable.cocktail_0,
                R.drawable.cocktail_1,
                R.drawable.cocktail_2,
                R.drawable.cocktail_3,
                R.drawable.cocktail_4,
                R.drawable.cocktail_5,
                R.drawable.cocktail_6,
                R.drawable.cocktail_7,
                R.drawable.cocktail_8,
                R.drawable.cocktail_9),
            R.drawable.cocktail_prcnt)

        val widget = Widget(
            4,
            WidgetNameKey.COCKTAIL,
            WidgetNameKey.COCKTAIL,
            LockedStatus.LOCKED,
            true,
            template,
            null
        )

        list.add(widget)
    }

    private fun addDuck() {
        val template = Template(
            WidgetNameKey.DUCK,
            TemplateTypes.MASK_DUCK,
            intArrayOf(R.drawable.duck_full, R.drawable.duck_empty, R.drawable.duck_level),
            intArrayOf(R.drawable.duck_level_mask, R.drawable.duck_mask_2),
            null,
            intArrayOf(
                R.drawable.duck_0,
                R.drawable.duck_1,
                R.drawable.duck_2,
                R.drawable.duck_3,
                R.drawable.duck_4,
                R.drawable.duck_5,
                R.drawable.duck_6,
                R.drawable.duck_7,
                R.drawable.duck_8,
                R.drawable.duck_9),
            R.drawable.duck_prcnt)

        val widget = Widget(
            5,
            WidgetNameKey.DUCK,
            WidgetNameKey.DUCK,
            LockedStatus.LOCKED,
            true,
            template,
            null
        )

        list.add(widget)
    }

    private fun addPizza() {
        val template = Template(
            WidgetNameKey.PIZZA,
            TemplateTypes.IMAGES_SEQ,
            intArrayOf(
                R.drawable.pizza_main_100,
                R.drawable.pizza_main_96,
                R.drawable.pizza_main_92,
                R.drawable.pizza_main_88,
                R.drawable.pizza_main_84,
                R.drawable.pizza_main_80,
                R.drawable.pizza_main_76,
                R.drawable.pizza_main_72,
                R.drawable.pizza_main_68,
                R.drawable.pizza_main_64,
                R.drawable.pizza_main_60,
                R.drawable.pizza_main_56,
                R.drawable.pizza_main_52,
                R.drawable.pizza_main_48,
                R.drawable.pizza_main_44,
                R.drawable.pizza_main_40,
                R.drawable.pizza_main_36,
                R.drawable.pizza_main_32,
                R.drawable.pizza_main_28,
                R.drawable.pizza_main_24,
                R.drawable.pizza_main_20,
                R.drawable.pizza_main_16,
                R.drawable.pizza_main_12,
                R.drawable.pizza_main_8,
                R.drawable.pizza_main_4),
            null,
            null,
            intArrayOf(
                R.drawable.pizza_0,
                R.drawable.pizza_1,
                R.drawable.pizza_2,
                R.drawable.pizza_3,
                R.drawable.pizza_4,
                R.drawable.pizza_5,
                R.drawable.pizza_6,
                R.drawable.pizza_7,
                R.drawable.pizza_8,
                R.drawable.pizza_9),
            R.drawable.pizza_prcnt)

        val widget = Widget(
            6,
            WidgetNameKey.PIZZA,
            WidgetNameKey.PIZZA,
            LockedStatus.LOCKED,
            true,
            template,
            null
        )

        list.add(widget)
    }

    private fun addZodiac() {
        val template = Template(
            WidgetNameKey.ZODIAC,
            TemplateTypes.MASK_D,
            intArrayOf(
                R.drawable.aries,
                R.drawable.taurus,
                R.drawable.gemini,
                R.drawable.cancer,
                R.drawable.leo,
                R.drawable.virgo,
                R.drawable.libra,
                R.drawable.scorpio,
                R.drawable.sagittarius,
                R.drawable.capricorn,
                R.drawable.aquarius,
                R.drawable.pisces
            ),
            intArrayOf(
                R.drawable.zodiac_segment_1,
                R.drawable.zodiac_segment_2
            ),
            intArrayOf(
                R.drawable.zodiac_last
            ),
            intArrayOf(
                R.drawable.zodiac_0,
                R.drawable.zodiac_1,
                R.drawable.zodiac_2,
                R.drawable.zodiac_3,
                R.drawable.zodiac_4,
                R.drawable.zodiac_5,
                R.drawable.zodiac_6,
                R.drawable.zodiac_7,
                R.drawable.zodiac_8,
                R.drawable.zodiac_9),
            R.drawable.zodiac_prcnt
        )

        val widget = Widget(
            7,
            WidgetNameKey.ZODIAC,
            WidgetNameKey.ZODIAC,
            LockedStatus.LOCKED,
            true,
            template,
            Pair(0, ZodiacTypesMapper.toIntArray())
        )

        list.add(widget)
    }

    private fun addDonut() {
        val template = Template(
            WidgetNameKey.DONUT,
            TemplateTypes.IMAGES_SEQ,
            intArrayOf(
                R.drawable.donut_main_100,
                R.drawable.donut_main_98,
                R.drawable.donut_main_95,
                R.drawable.donut_main_90,
                R.drawable.donut_main_85,
                R.drawable.donut_main_80,
                R.drawable.donut_main_75,
                R.drawable.donut_main_70,
                R.drawable.donut_main_65,
                R.drawable.donut_main_60,
                R.drawable.donut_main_55,
                R.drawable.donut_main_50,
                R.drawable.donut_main_45,
                R.drawable.donut_main_40,
                R.drawable.donut_main_35,
                R.drawable.donut_main_30,
                R.drawable.donut_main_25,
                R.drawable.donut_main_20,
                R.drawable.donut_main_15,
                R.drawable.donut_main_10,
                R.drawable.donut_main_5
            ),
            null,
            null,
            intArrayOf(
                R.drawable.donut_0,
                R.drawable.donut_1,
                R.drawable.donut_2,
                R.drawable.donut_3,
                R.drawable.donut_4,
                R.drawable.donut_5,
                R.drawable.donut_6,
                R.drawable.donut_7,
                R.drawable.donut_8,
                R.drawable.donut_9),
            R.drawable.donut_prcent)

        val widget = Widget(
            8,
            WidgetNameKey.DONUT,
            WidgetNameKey.DONUT,
            LockedStatus.LOCKED,
            true,
            template,
            null
        )

        list.add(widget)
    }

    companion object {

        fun create(): List<Widget> {
            val widgetList = WidgetsFactory()

            widgetList.apply {
                addAqarium()
                addManeki()
                addCoffee()
                addHeart()
                addCocktail()
                addDuck()
                addPizza()
                addZodiac()
                addDonut()
            }

            return widgetList.getWidgetList()

        }

        fun createDefaultWidget() : Widget{
            return WidgetsFactory().createAquarium()
        }
    }
}