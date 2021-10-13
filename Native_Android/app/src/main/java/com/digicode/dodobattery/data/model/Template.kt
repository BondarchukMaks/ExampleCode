package com.digicode.dodobattery.data.model

class Template(
    val name: String,
    val type: String,
    val mainImage: IntArray?,
    val mask: IntArray?,
    val cover: IntArray?,
    val numbers: IntArray?,
    val percents: Int
)

class TemplateTypes {

    companion object {
        const val MASK_A = "mask_a"
        const val MASK_B = "mask_b"
        const val MASK_C = "mask_c"
        const val MASK_D = "mask_d"
        const val MASK_DUCK = "duck_mask"
        const val MASK_MANEKI = "maneki_mask"
        const val MASK_AQARIUM = "aqarium_mask"
        const val MASK_COFFEE = "mask_coffee"
        const val MASK_HEART = "mask_heart"
        const val MASK_COCKTAIL = "mask_cocktail"
        const val IMAGES_SEQ = "images_sequence"
    }
}