package com.commit451.resourcespoet;

/**
 * The various resource types
 *
 * @see <a href="https://developer.android.com/guide/topics/resources/available-resources.html">https://developer.android.com/guide/topics/resources/available-resources.html</a>
 */
public enum Type {

    ATTR {
        @Override
        public String toString() {
            return "attr";
        }
    },
    BOOL {
        @Override
        public String toString() {
            return "bool";
        }
    },
    COLOR {
        @Override
        public String toString() {
            return "color";
        }
    },
    DRAWABLE {
        @Override
        public String toString() {
            return "drawable";
        }
    },
    DIMENSION {
        @Override
        public String toString() {
            return "dimen";
        }
    },
    ID {
        @Override
        public String toString() {
            return "item";
        }
    },
    INTEGER {
        @Override
        public String toString() {
            return "integer";
        }
    },
    INTEGER_ARRAY {
        @Override
        public String toString() {
            return "integer-array";
        }
    },
    PLURALS {
        @Override
        public String toString() {
            return "plurals";
        }
    },
    STRING {
        @Override
        public String toString() {
            return "string";
        }
    },
    STRING_ARRAY {
        @Override
        public String toString() {
            return "string-array";
        }
    },
    STYLE {
        @Override
        public String toString() {
            return "style";
        }
    },
    TYPED_ARRAY {
        @Override
        public String toString() {
            return "array";
        }
    }

}
