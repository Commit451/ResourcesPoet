package com.commit451.resourcespoet

/**
 * Represents an Android Font
 * See <a https:></a>//developer.android.com/guide/topics/ui/look-and-feel/fonts-in-xml">https://developer.android.com/guide/topics/ui/look-and-feel/fonts-in-xml
 */
data class FontFamily
/**
 * Construct a Font family
 * @param fontStyle the font style
 * @param fontWeight the font weight value
 * @param font reference to the font file
 */
    (val fontStyle: String, val fontWeight: String, val font: String)
