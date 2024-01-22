package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests the resources creation
 */
class StringTest {

    @Test
    fun stringTest() {
        val poet = ResourcesPoet.create()
            .addString("app_name", "Test")

        TestUtil.assertEquals("string.xml", poet)
    }

    @Test
    fun stringTranslatableTest() {
        val poet = ResourcesPoet.create()
            .addString("app_name", "Test", false)

        TestUtil.assertEquals("string_translatable_false.xml", poet)
    }
}
