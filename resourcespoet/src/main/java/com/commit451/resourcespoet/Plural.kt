package com.commit451.resourcespoet

/**
 * Represents an Android Plural
 * See <a https:></a>//developer.android.com/guide/topics/resources/string-resource.html#Plurals">https://developer.android.com/guide/topics/resources/string-resource.html#Plurals
 */
class Plural
/**
 * Construct a new plural
 * @param quantity the quantity
 * @param value the value
 */
(var quantity: Quantity, var value: String) {

    enum class Quantity {
        zero,
        one,
        two,
        few,
        many,
        other
    }
}
