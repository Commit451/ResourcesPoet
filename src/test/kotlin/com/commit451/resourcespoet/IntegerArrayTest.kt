package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests the resources creation
 */
class IntegerArrayTest {

    @Test
    fun integerArrayTest() {
        val numbers = ArrayList<Int>()
        numbers.add(1)
        numbers.add(2)

        val poet = ResourcesPoet.create()
            .addIntegerArray("numbers", numbers)

        TestUtil.assertEquals("integer_array.xml", poet)
    }
}
