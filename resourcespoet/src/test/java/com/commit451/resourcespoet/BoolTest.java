package com.commit451.resourcespoet;

import org.junit.Test;

/**
 * Tests the resources creation
 */
public class BoolTest {

    @Test
    public void boolTest() throws Exception {
        ResourcesPoet poet = ResourcesPoet.create()
                .addBool("is_cool", true);

        TestUtil.assertEquals("bool.xml", poet);
    }
}
