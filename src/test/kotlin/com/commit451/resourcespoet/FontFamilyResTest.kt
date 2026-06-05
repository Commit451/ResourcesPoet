package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests FontFamilyRes resource creation
 */
class FontFamilyResTest {

    @Test
    fun fontFamilyResTest() {
        val poet = ResourcesPoet.create(elementType = ResourcesPoet.Companion.ELEMENT.FONT_FAMILIES)
            .addFontFamilyRes(FontFamilyRes("normal", "400", 2131427392))

        TestUtil.assertEquals("font_family_res.xml", poet)
    }

    @Test
    fun fontFamilyResWithToolsIgnore() {
        val poet = ResourcesPoet.create(elementType = ResourcesPoet.Companion.ELEMENT.FONT_FAMILIES)
            .addFontFamilyRes(FontFamilyRes("normal", "400", 2131427392), toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("font_family_res_tools_ignore.xml", poet)
    }
}
