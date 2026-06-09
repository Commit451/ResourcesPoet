package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests reference resource creation
 */
class ReferenceTest {

    @Test
    fun referenceWithType() {
        val poet = ResourcesPoet.create()
            .addReference("logo", Reference("drawable", "logo"))

        TestUtil.assertEquals("reference.xml", poet)
    }

    @Test
    fun referenceWithToolsIgnore() {
        val poet = ResourcesPoet.create()
            .addReference("logo", Reference("drawable", "logo"), toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("reference_tools_ignore.xml", poet)
    }

    @Test
    fun referenceConvenienceOverload() {
        val poet = ResourcesPoet.create()
            .addReference("logo", "drawable", "logo")

        TestUtil.assertEquals("reference.xml", poet)
    }
}
