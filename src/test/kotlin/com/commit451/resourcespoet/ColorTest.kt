package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests the resources creation
 */
class ColorTest {

    @Test
    fun colorTest() {
        val poet = ResourcesPoet.create()
            .addColor("color_primary", "#FF0000")

        TestUtil.assertEquals("color.xml", poet)
    }
}
