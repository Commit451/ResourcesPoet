@file:Suppress("unused")

package com.commit451.resourcespoet

/**
 * The values within an attribute
 */
class Attr {

    var name: String
    val formats = mutableListOf<Format>()

    enum class Format constructor(private val attrName: String) {
        BOOLEAN("boolean"),
        FLOAT("float"),
        COLOR("color"),
        DIMENSION("dimension"),
        ENUM("enum"),
        FLAG("flag"),
        FRACTION("fraction"),
        INTEGER("integer"),
        REFERENCE("reference"),
        STRING("string");

        override fun toString(): String {
            return attrName
        }
    }

    /**
     * Construct a new attribute
     * @param name the attribute name
     * @param format the format, or `null` if no format
     */
    constructor(name: String, format: Format? = null) {
        this.name = name
        if (format != null) {
            formats.add(format)
        }
    }

    /**
     * Construct a new attribute
     * @param name the attribute name
     * @param formats the accepted formats, or `null` if no formats
     */
    constructor(name: String, formats: List<Format>? = null) {
        this.name = name
        if (formats != null) {
            this.formats.addAll(formats)
        }
    }
}
