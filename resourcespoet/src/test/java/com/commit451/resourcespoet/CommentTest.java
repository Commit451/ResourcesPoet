package com.commit451.resourcespoet;

import org.junit.Test;

/**
 * Tests the resources creation
 */
public class CommentTest {

    @Test
    public void commentTest() throws Exception {
        ResourcesPoet poet = ResourcesPoet.create()
                .addComment("This is a comment");

        TestUtil.assertEquals("comment.xml", poet);
    }
}
