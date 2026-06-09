package com.commit451.resourcespoet

/**
 * Represents an Android resource reference, e.g. `@drawable/logo`
 * @param type the reference type (e.g. "drawable", "color")
 * @param name the resource name
 */
data class Reference(
    val type: String,
    val name: String
) {
    /**
     * Format the reference as an Android resource reference string
     * e.g. "@drawable/logo" or "@color/primary" or "@string/app_name"
     */
    fun toValue(): String = "@$type/$name"
}
