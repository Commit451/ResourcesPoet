package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests translatable attribute on string-array
 */
class StringArrayTranslatableTest {

    @Test
    fun stringArrayTranslatableFalse() {
        val poet = ResourcesPoet.create()
            .addStringArray("stuff", listOf("One", "Two"), translatable = false)

        TestUtil.assertEquals("string_array_translatable_false.xml", poet)
    }

    @Test
    fun stringArrayTranslatableTrue() {
        val poet = ResourcesPoet.create()
            .addStringArray("stuff", listOf("One", "Two"), translatable = true)

        TestUtil.assertEquals("string_array.xml", poet)
    }
}
