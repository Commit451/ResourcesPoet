package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests the resources creation
 */
class TypedArrayTest {

    @Test
    fun typedArrayTest() {
        val strings = ArrayList<String>()
        strings.add("One")
        strings.add("Two")

        val poet = ResourcesPoet.create()
            .addTypedArray("some_typed_array", strings)

        TestUtil.assertEquals("typed_array.xml", poet)
    }
}
