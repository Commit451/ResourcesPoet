package com.commit451.resourcespoet;

/**
 * Represents an Android Plural
 * See <a https://developer.android.com/guide/topics/resources/string-resource.html#Plurals">https://developer.android.com/guide/topics/resources/string-resource.html#Plurals</a>
 */
public class Plural {

    public enum Quantity {
        zero,
        one,
        two,
        few,
        many,
        other
    }

    public String value;
    public Quantity quantity;

    /**
     * Construct a new plural
     * @param quantity the quantity
     * @param value the value
     */
    public Plural(Quantity quantity, String value) {
        this.value = value;
        this.quantity = quantity;
    }
}
