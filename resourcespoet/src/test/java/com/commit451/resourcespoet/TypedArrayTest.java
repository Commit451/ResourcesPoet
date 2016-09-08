package com.commit451.resourcespoet;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Tests the resources creation
 */
public class TypedArrayTest {

    @Test
    public void typedArrayTest() throws Exception {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("One");
        strings.add("Two");

        ResourcesPoet poet = ResourcesPoet.create()
                .addTypedArray("some_typed_array", strings);

        TestUtil.assertEquals("typed_array.xml", poet);
    }
}
