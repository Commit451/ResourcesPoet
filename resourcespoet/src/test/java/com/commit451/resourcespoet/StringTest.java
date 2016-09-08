package com.commit451.resourcespoet;

import org.junit.Test;

/**
 * Tests the resources creation
 */
public class StringTest {

    @Test
    public void stringTest() throws Exception {
        ResourcesPoet poet = ResourcesPoet.create()
                .addString("app_name", "Test");

        TestUtil.assertEquals("string.xml", poet);
    }
}
