package com.commit451.resourcespoet;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

/**
 * Tests the resources creation
 */
public class ConfigTest {

    @Test
    public void creationTest() throws Exception {

        ArrayList<String> strings = new ArrayList<>();
        strings.add("One");
        strings.add("Two");

        ArrayList<Plural> plurals = new ArrayList<>();
        plurals.add(new Plural(Plural.Quantity.one, "%d song"));
        plurals.add(new Plural(Plural.Quantity.other, "%d songs"));

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);

        ArrayList<String> typedArray = new ArrayList<>();
        typedArray.add("One");
        typedArray.add("Two");

        ResourcesPoet poet = ResourcesPoet.create()
                .addBool("is_cool", true)
                .addColor("color_primary", "#FF0000")
                .addComment("This is a comment")
                .addDimension("margin", "2dp")
                .addDrawable("logo", "@drawable/logo")
                .addId("some_id")
                .addInteger("number", 0)
                .addIntegerArray("numbers", numbers)
                .addPlurals("songs", plurals)
                .addString("app_name", "Test")
                .addStringArray("stuff", strings)
                .addStyle("AppTheme.Dark", "Base.AppTheme.Dark")
                .addTypedArray("some_typed_array", typedArray);

        TestUtil.assertEquals("config.xml", poet);
    }

    @Test
    public void addToExistingFile() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("config.xml").getFile());
        ResourcesPoet poet = ResourcesPoet.create(file)
                .addString("added", "this one was added");

        TestUtil.assertEquals("config_with_extra_string.xml", poet);
    }
}
