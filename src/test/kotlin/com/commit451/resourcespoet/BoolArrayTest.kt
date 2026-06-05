package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests bool-array resource creation
 */
class BoolArrayTest {

    @Test
    fun boolArrayTest() {
        val poet = ResourcesPoet.create()
            .addBoolArray("flags", listOf(true, false))

        TestUtil.assertEquals("bool_array.xml", poet)
    }

    @Test
    fun boolArrayWithToolsIgnore() {
        val poet = ResourcesPoet.create()
            .addBoolArray("flags", listOf(true, false), toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("bool_array_tools_ignore.xml", poet)
    }
}
