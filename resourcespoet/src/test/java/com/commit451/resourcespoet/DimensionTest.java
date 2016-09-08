package com.commit451.resourcespoet;

import org.junit.Test;

/**
 * Tests the resources creation
 */
public class DimensionTest {

    @Test
    public void dimensionTest() throws Exception {
        ResourcesPoet poet = ResourcesPoet.create()
                .addDimension("margin", "2dp");

        TestUtil.assertEquals("dimension.xml", poet);
    }
}
