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

    private String value;
    private Quantity quantity;

    public Plural(Quantity quantity, String value) {
        this.value = value;
        this.quantity = quantity;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public String getValue() {
        return value;
    }
}
