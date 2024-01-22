package com.commit451.resourcespoet

import org.junit.Ignore
import org.junit.Test

import java.io.File

/**
 * Tests the resources creation
 */
class RemoveTest {

    @Test
    @Ignore("broken due to indentations. Fix it!")
    fun removeTest() {
        val classLoader = javaClass.classLoader
        val file = File(classLoader.getResource("remove_before.xml")!!.file)
        val poet = ResourcesPoet.create(file)
            .remove(Type.STRING, "red")

        TestUtil.assertEquals("remove_after.xml", poet)
    }
}
