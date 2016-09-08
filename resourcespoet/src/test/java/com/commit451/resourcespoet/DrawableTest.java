package com.commit451.resourcespoet;

import org.junit.Test;

/**
 * Tests the resources creation
 */
public class DrawableTest {

    @Test
    public void drawableTest() throws Exception {
        ResourcesPoet poet = ResourcesPoet.create()
                .addDrawable("logo", "@drawable/logo");

        TestUtil.assertEquals("drawable.xml", poet);
    }
}
