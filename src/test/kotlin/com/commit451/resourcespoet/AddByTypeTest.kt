package com.commit451.resourcespoet

import org.junit.Assert
import org.junit.Test

/**
 * Tests the resources creation
 */
class AddByTypeTest {

    @Test
    fun addStringType() {
        val poet = ResourcesPoet.create()
                .add(Type.STRING, "app_name", "Test")

        TestUtil.assertEquals("string.xml", poet)
    }

    @Test
    fun addIntegerType() {
        val poet = ResourcesPoet.create()
                .add(Type.INTEGER, "number", "0")

        TestUtil.assertEquals("integer.xml", poet)
    }

    @Test
    fun addUnsupportedType() {
        var exception: Exception? = null
        try {
            ResourcesPoet.create()
                    .add(Type.INTEGER_ARRAY, "fail", "fail")
        } catch (e: Exception) {
            exception = e
        }

        Assert.assertNotNull(exception)
        Assert.assertTrue(exception is IllegalArgumentException)
    }
}
