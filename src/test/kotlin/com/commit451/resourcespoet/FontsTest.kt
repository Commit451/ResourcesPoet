package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests the font resource creation
 */
class FontsTest {

    @Test
    fun styleTest() {
        val poet = ResourcesPoet.create(elementType = ResourcesPoet.Companion.ELEMENT.FONT_FAMILIES)
            .addFontFamily(FontFamily("normal", "400", "@font/lobster_regular"))
        TestUtil.assertEquals("fonts.xml", poet)
    }
}
