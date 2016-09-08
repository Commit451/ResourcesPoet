package com.commit451.resourcespoet;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Tests the resources creation
 */
public class PluralsTest {

    @Test
    public void pluralsTest() throws Exception {
        ArrayList<Plural> plurals = new ArrayList<>();
        plurals.add(new Plural(Plural.Quantity.one, "%d song"));
        plurals.add(new Plural(Plural.Quantity.other, "%d songs"));

        ResourcesPoet poet = ResourcesPoet.create()
                .addPlurals("songs", plurals);

        TestUtil.assertEquals("plurals.xml", poet);
    }
}
