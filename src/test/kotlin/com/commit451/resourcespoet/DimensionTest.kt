package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests the resources creation
 */
class DimensionTest {

    @Test
    fun dimensionTest() {
        val poet = ResourcesPoet.create()
            .addDimension("margin", "2dp")

        TestUtil.assertEquals("dimension.xml", poet)
    }
}
