package com.commit451.resourcespoet;

import org.junit.Test;

/**
 * Tests the resources creation
 */
public class ColorTest {

    @Test
    public void colorTest() throws Exception {
        ResourcesPoet poet = ResourcesPoet.create()
                .addColor("color_primary", "#FF0000");

        TestUtil.assertEquals("color.xml", poet);
    }
}
