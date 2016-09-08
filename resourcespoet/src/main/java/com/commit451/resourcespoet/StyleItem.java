package com.commit451.resourcespoet;

/**
 * An item within a style, like
 *
 * <item name="android:windowBackground">@android:color/black</item>
 */
public class StyleItem {

    public String name;
    public String value;

    public StyleItem(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
