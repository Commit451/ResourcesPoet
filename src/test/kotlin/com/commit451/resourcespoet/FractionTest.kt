package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests fraction resource creation
 */
class FractionTest {

    @Test
    fun fractionTest() {
        val poet = ResourcesPoet.create()
            .addFraction("fraction_value", "50%p")

        TestUtil.assertEquals("fraction.xml", poet)
    }

    @Test
    fun fractionWithToolsIgnore() {
        val poet = ResourcesPoet.create()
            .addFraction("fraction_value", "50%p", toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("fraction_tools_ignore.xml", poet)
    }
}
