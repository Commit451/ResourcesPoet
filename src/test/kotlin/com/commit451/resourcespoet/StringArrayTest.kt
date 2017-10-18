package com.commit451.resourcespoet

import org.junit.Test
import java.util.*

/**
 * Tests the resources creation
 */
class StringArrayTest {

    @Test
    fun stringArrayTest() {
        val strings = ArrayList<String>()
        strings.add("One")
        strings.add("Two")

        val poet = ResourcesPoet.create()
                .addStringArray("stuff", strings)

        TestUtil.assertEquals("string_array.xml", poet)
    }
}
