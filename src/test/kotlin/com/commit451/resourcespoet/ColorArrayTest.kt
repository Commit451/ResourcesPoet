package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests color-array resource creation
 */
class ColorArrayTest {

    @Test
    fun colorArrayTest() {
        val poet = ResourcesPoet.create()
            .addColorArray("colors", listOf("#FF0000", "#00FF00"))

        TestUtil.assertEquals("color_array.xml", poet)
    }

    @Test
    fun colorArrayWithToolsIgnore() {
        val poet = ResourcesPoet.create()
            .addColorArray("colors", listOf("#FF0000", "#00FF00"), toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("color_array_tools_ignore.xml", poet)
    }
}
