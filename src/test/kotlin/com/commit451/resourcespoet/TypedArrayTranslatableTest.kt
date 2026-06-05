package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests translatable attribute on typed-array
 */
class TypedArrayTranslatableTest {

    @Test
    fun typedArrayTranslatableFalse() {
        val poet = ResourcesPoet.create()
            .addTypedArray("some_typed_array", listOf("One", "Two"), translatable = false)

        TestUtil.assertEquals("typed_array_translatable_false.xml", poet)
    }

    @Test
    fun typedArrayTranslatableTrue() {
        val poet = ResourcesPoet.create()
            .addTypedArray("some_typed_array", listOf("One", "Two"), translatable = true)

        TestUtil.assertEquals("typed_array.xml", poet)
    }
}
