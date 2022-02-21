package com.commit451.resourcespoet

/**
 * The various resource types
 *
 * [Available Resources](https://developer.android.com/guide/topics/resources/available-resources.html)
 */
enum class Type(private val xmlName: String) {

    ATTR("attr"),
    BOOL("bool"),
    COLOR("color"),
    DRAWABLE("drawable"),
    DIMENSION("dimen"),
    ID("item"),
    INTEGER("integer"),
    INTEGER_ARRAY("integer-array"),
    PLURALS("plurals"),
    STRING("string"),
    STRING_ARRAY("string-array"),
    STYLE("style"),
    TYPED_ARRAY("array"),
    FONT("font");

    override fun toString() = xmlName
}
