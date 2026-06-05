package com.commit451.resourcespoet

/**
 * Represents an Android int-quantity resource
 * @param quantity the quantity (zero, one, two, few, many, other)
 * @param value the integer value
 * See [the Android docs](https://developer.android.com/guide/topics/resources/string-resource.html#Plurals)
 */
data class Quantity(
    val quantity: Plural.Quantity,
    val value: Int
)
