package com.commit451.resourcespoet

/**
 * The various resource types
 *
 * @see [https://developer.android.com/guide/topics/resources/available-resources.html](https://developer.android.com/guide/topics/resources/available-resources.html)
 */
enum class Type {

    ATTR {
        override fun toString(): String {
            return "attr"
        }
    },
    BOOL {
        override fun toString(): String {
            return "bool"
        }
    },
    COLOR {
        override fun toString(): String {
            return "color"
        }
    },
    DRAWABLE {
        override fun toString(): String {
            return "drawable"
        }
    },
    DIMENSION {
        override fun toString(): String {
            return "dimen"
        }
    },
    ID {
        override fun toString(): String {
            return "item"
        }
    },
    INTEGER {
        override fun toString(): String {
            return "integer"
        }
    },
    INTEGER_ARRAY {
        override fun toString(): String {
            return "integer-array"
        }
    },
    PLURALS {
        override fun toString(): String {
            return "plurals"
        }
    },
    STRING {
        override fun toString(): String {
            return "string"
        }
    },
    STRING_ARRAY {
        override fun toString(): String {
            return "string-array"
        }
    },
    STYLE {
        override fun toString(): String {
            return "style"
        }
    },
    TYPED_ARRAY {
        override fun toString(): String {
            return "array"
        }
    }

}
