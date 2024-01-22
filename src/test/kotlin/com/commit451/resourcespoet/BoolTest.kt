package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests the resources creation
 */
class BoolTest {

    @Test
    fun boolTest() {
        val poet = ResourcesPoet.create()
            .addBool("is_cool", true)

        TestUtil.assertEquals("bool.xml", poet)
    }
}
