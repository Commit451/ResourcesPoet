package com.commit451.resourcespoet;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Tests the resources creation
 */
public class StringArrayTest {

    @Test
    public void stringArrayTest() throws Exception {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("One");
        strings.add("Two");

        ResourcesPoet poet = ResourcesPoet.create()
                .addStringArray("stuff", strings);

        TestUtil.assertEquals("string_array.xml", poet);
    }
}
