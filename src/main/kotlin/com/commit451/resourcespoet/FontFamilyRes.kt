package com.commit451.resourcespoet

/**
 * Represents an Android font-family resource using an integer resource reference
 * @param fontStyle the font style (e.g. "normal", "italic")
 * @param fontWeight the font weight (e.g. "400", "700")
 * @param fontRes the integer resource ID of the font
 * See [the Android docs](https://developer.android.com/guide/topics/ui/look-and-feel/fonts-in-xml)
 */
data class FontFamilyRes(
    val fontStyle: String,
    val fontWeight: String,
    val fontRes: Int
)
