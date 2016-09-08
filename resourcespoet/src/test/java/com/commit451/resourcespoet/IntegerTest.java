package com.commit451.resourcespoet;

import org.junit.Test;

/**
 * Tests the resources creation
 */
public class IntegerTest {

    @Test
    public void integerTest() throws Exception {
        ResourcesPoet poet = ResourcesPoet.create()
                .addInteger("number", 0);

        TestUtil.assertEquals("integer.xml", poet);
    }
}
