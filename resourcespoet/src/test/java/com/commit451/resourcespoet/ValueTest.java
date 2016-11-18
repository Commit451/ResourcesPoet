package com.commit451.resourcespoet;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

/**
 * Tests reading a value
 */
public class ValueTest {

    @Test
    public void valueTest() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("bool.xml").getFile());
        ResourcesPoet poet = ResourcesPoet.create(file);
        String value = poet.value(Type.BOOL, "is_cool");
        Assert.assertEquals("true", value);
    }
}
