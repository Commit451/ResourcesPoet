package com.commit451.resourcespoet;

import org.junit.Test;

/**
 * Tests the resources creation
 */
public class IdTest {

    @Test
    public void idTest() throws Exception {
        ResourcesPoet poet = ResourcesPoet.create()
                .addId("some_id");

        TestUtil.assertEquals("id.xml", poet);
    }
}
