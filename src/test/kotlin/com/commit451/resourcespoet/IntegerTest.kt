package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests the resources creation
 */
class IntegerTest {

    @Test
    fun integerTest() {
        val poet = ResourcesPoet.create()
            .addInteger("number", 0)

        TestUtil.assertEquals("integer.xml", poet)
    }
}
