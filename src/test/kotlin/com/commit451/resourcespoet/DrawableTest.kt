package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests the resources creation
 */
class DrawableTest {

    @Test
    fun drawableTest() {
        val poet = ResourcesPoet.create()
            .addDrawable("logo", "@drawable/logo")

        TestUtil.assertEquals("drawable.xml", poet)
    }
}
