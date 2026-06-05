package com.commit451.resourcespoet

/**
 * Represents an Android @reference resource
 * @param type the reference type (e.g. "drawable", "color"), or null for a generic reference
 * @param name the resource name
 */
data class Reference(
    val type: String? = null,
    val name: String
) {
    /**
     * Format the reference as an Android resource reference string
     * e.g. "@drawable/logo" or "@color/primary" or "@string/app_name"
     */
    fun toValue(): String = "@${if (type != null) "$type/" else ""}$name"
}
