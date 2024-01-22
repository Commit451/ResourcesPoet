package com.commit451.resourcespoet

import org.junit.Ignore
import org.junit.Test
import java.io.File

/**
 * Tests the resources creation
 */
class ConfigTest {

    @Test
    fun creationTest() {

        val strings = ArrayList<String>()
        strings.add("One")
        strings.add("Two")

        val plurals = ArrayList<Plural>()
        plurals.add(Plural(Plural.Quantity.one, "%d song"))
        plurals.add(Plural(Plural.Quantity.other, "%d songs"))

        val numbers = ArrayList<Int>()
        numbers.add(1)
        numbers.add(2)

        val typedArray = ArrayList<String>()
        typedArray.add("One")
        typedArray.add("Two")

        val poet = ResourcesPoet.create()
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
            .addTypedArray("some_typed_array", typedArray)

        TestUtil.assertEquals("config.xml", poet)
    }

    @Test
    @Ignore("broken due to indentations. Fix it!")
    fun addToExistingFile() {
        val classLoader = javaClass.classLoader
        val file = File(classLoader.getResource("config.xml")!!.file)
        val poet = ResourcesPoet.create(file)
            .addString("added", "this one was added")

        TestUtil.assertEquals("config_with_extra_string.xml", poet)
    }
}
