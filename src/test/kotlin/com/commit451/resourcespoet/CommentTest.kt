package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests the resources creation
 */
class CommentTest {

    @Test
    fun commentTest() {
        val poet = ResourcesPoet.create()
                .addComment("This is a comment")

        TestUtil.assertEquals("comment.xml", poet)
    }
}
