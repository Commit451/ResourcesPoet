package com.commit451.resourcespoet;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Tests the resources creation
 */
public class IntegerArrayTest {

    @Test
    public void integerArrayTest() throws Exception {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);

        ResourcesPoet poet = ResourcesPoet.create()
                .addIntegerArray("numbers", numbers);

        TestUtil.assertEquals("integer_array.xml", poet);
    }
}
