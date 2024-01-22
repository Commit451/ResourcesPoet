package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests the resources creation
 */
class IdTest {

    @Test
    fun idTest() {
        val poet = ResourcesPoet.create()
            .addId("some_id")

        TestUtil.assertEquals("id.xml", poet)
    }
}
