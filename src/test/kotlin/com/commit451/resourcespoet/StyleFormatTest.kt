package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests format attribute on styles
 */
class StyleFormatTest {

    @Test
    fun styleWithFormat() {
        val styleItems = ArrayList<StyleItem>()
        styleItems.add(StyleItem("android:windowBackground", "@android:color/white"))
        val poet = ResourcesPoet.create()
            .addStyle("AppTheme", parentRef = "Base.AppTheme", styleItems = styleItems, format = "string|reference")

        TestUtil.assertEquals("style_format.xml", poet)
    }

    @Test
    fun styleWithFormatAndToolsIgnore() {
        val styleItems = ArrayList<StyleItem>()
        styleItems.add(StyleItem("android:windowBackground", "@android:color/white"))
        val poet = ResourcesPoet.create()
            .addStyle("AppTheme", parentRef = "Base.AppTheme", styleItems = styleItems, format = "string|reference", toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("style_format_tools_ignore.xml", poet)
    }

    @Test
    fun styleWithoutFormat() {
        val styleItems = ArrayList<StyleItem>()
        styleItems.add(StyleItem("android:windowBackground", "@android:color/white"))
        val poet = ResourcesPoet.create()
            .addStyle("AppTheme", parentRef = "Base.AppTheme", styleItems = styleItems)

        TestUtil.assertEquals("style_without_format.xml", poet)
    }
}
