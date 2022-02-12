@file:Suppress("EnumEntryName", "unused")

package com.commit451.resourcespoet

/**
 * Represents an Android Plural
 * See <a https:></a>//developer.android.com/guide/topics/resources/string-resource.html#Plurals">https://developer.android.com/guide/topics/resources/string-resource.html#Plurals
 */
data class Plural
/**
 * Construct a new plural
 * @param quantity the quantity
 * @param value the value
 */
(val quantity: Quantity, val value: String) {

    enum class Quantity {
        zero,
        one,
        two,
        few,
        many,
        other
    }
}
