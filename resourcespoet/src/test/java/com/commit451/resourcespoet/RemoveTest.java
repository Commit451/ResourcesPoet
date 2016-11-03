package com.commit451.resourcespoet;

import org.junit.Test;

import java.io.File;

/**
 * Tests the resources creation
 */
public class RemoveTest {

    @Test
    public void removeTest() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("remove_before.xml").getFile());
        ResourcesPoet poet = ResourcesPoet.create(file)
                .remove(Type.STRING, "red");

        TestUtil.assertEquals("remove_after.xml", poet);
    }
}
