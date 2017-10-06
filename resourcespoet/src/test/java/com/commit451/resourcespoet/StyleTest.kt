package com.commit451.resourcespoet

import org.junit.Test
import java.util.*

/**
 * Tests the resources creation
 */
class StyleTest {

    @Test
    fun styleTest() {
        val styleItems = ArrayList<StyleItem>()
        styleItems.add(StyleItem("android:windowBackground", "@android:color/white"))
        styleItems.add(StyleItem("colorPrimaryDark", "@android:color/black"))
        val poet = ResourcesPoet.create()
                .addStyle("AppTheme.Dark", "Base.AppTheme.Dark", styleItems)

        TestUtil.assertEquals("style.xml", poet)
    }
}
