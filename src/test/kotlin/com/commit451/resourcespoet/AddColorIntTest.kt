package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests addColor with Int value
 */
class AddColorIntTest {

    @Test
    fun colorIntTest() {
        val poet = ResourcesPoet.create()
            .addColor("color_primary", 0x0000FF)

        TestUtil.assertEquals("color_int.xml", poet)
    }

    @Test
    fun colorIntWithToolsIgnore() {
        val poet = ResourcesPoet.create()
            .addColor("color_primary", 0x0000FF, toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("color_int_tools_ignore.xml", poet)
    }

    @Test
    fun colorIntStripsAlpha() {
        // 0xFF0000FF should become #0000FF (alpha stripped)
        val poet = ResourcesPoet.create()
            .addColor("color_primary", (0xFF0000FFL.toInt()))

        val xml = poet.build()
        assert(xml.contains("#0000FF"))
    }
}
