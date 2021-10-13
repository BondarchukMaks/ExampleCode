package com.digicode.dodobattery.data.model.enums

import androidx.annotation.StringRes
import com.digicode.dodobattery.R

enum class ZodiacTypes(@StringRes val nameResId: Int) {
    ARIES(R.string.aries),
    TAURUS(R.string.taurus),
    GEMINI(R.string.gemini),
    CANCER(R.string.cancer),
    LEO(R.string.leo),
    VIRGO(R.string.virgo),
    LIBRA(R.string.libra),
    SCORPIO(R.string.scorpio),
    SAGITTARIUS(R.string.sagittarius),
    CAPRICORN(R.string.capricorn),
    AQUARIUS(R.string.aquarius),
    PISCES(R.string.pisces)
}

class ZodiacTypesMapper {

    companion object {

        fun toIntArray(): IntArray {
            return ZodiacTypes.values().map { it.nameResId }.toIntArray()
        }
    }
}