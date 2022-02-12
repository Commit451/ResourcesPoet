package com.commit451.resourcespoet

import org.junit.Assert
import org.junit.Test

import java.io.File

/**
 * Tests reading a value
 */
class ValueTest {

    @Test
    fun valueTest() {
        val classLoader = javaClass.classLoader
        val file = File(classLoader.getResource("bool.xml")!!.file)
        val poet = ResourcesPoet.create(file)
        val value = poet.value(Type.BOOL, "is_cool")
        Assert.assertEquals("true", value)
    }
}
