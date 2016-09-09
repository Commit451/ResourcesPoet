package com.commit451.resourcespoet;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * The values within an attribute
 */
public class Attr {

    public enum Format {
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

        private final String name;

        Format(String s) {
            name = s;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public String name;
    public List<Format> formats;

    /**
     * Construct a new attribute
     * @param name the attribute name
     * @param format the format, or {@code null} if no format
     */
    public Attr(String name, @Nullable Format format) {
        this.name = name;
        if (format != null) {
            formats = Collections.singletonList(format);
        }
    }

    /**
     * Construct a new attribute
     * @param name the attribute name
     * @param formats the accepted formats, or {@code null} if no formats
     */
    public Attr(String name, @Nullable List<Format> formats) {
        this.name = name;
        this.formats = formats;
    }
}
