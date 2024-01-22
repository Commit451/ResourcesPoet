@file:Suppress("EnumEntryName", "unused")

package com.commit451.resourcespoet

/**
 * Represents an Android Plural
 * @param quantity the quantity
 * @param value the value
 * See [the Android docs](https://developer.android.com/guide/topics/resources/string-resource.html#Plurals)
 */
data class Plural(
    val quantity: Quantity,
    val value: String,
) {

    enum class Quantity {
        zero,
        one,
        two,
        few,
        many,
        other
    }
}
