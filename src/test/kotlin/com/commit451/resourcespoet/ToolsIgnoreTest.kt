package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests the tools:ignore attribute feature
 */
class ToolsIgnoreTest {

    @Test
    fun stringWithToolsIgnore() {
        val poet = ResourcesPoet.create()
            .addString("app_name", "Test", toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("string_tools_ignore.xml", poet)
    }

    @Test
    fun stringWithToolsIgnoreAndTranslatable() {
        val poet = ResourcesPoet.create()
            .addString("app_name", "Test", translatable = false, toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("string_tools_ignore_translatable.xml", poet)
    }

    @Test
    fun boolWithToolsIgnore() {
        val poet = ResourcesPoet.create()
            .addBool("is_cool", true, toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("bool_tools_ignore.xml", poet)
    }

    @Test
    fun colorWithToolsIgnore() {
        val poet = ResourcesPoet.create()
            .addColor("color_primary", "#FF0000", toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("color_tools_ignore.xml", poet)
    }

    @Test
    fun drawableWithToolsIgnore() {
        val poet = ResourcesPoet.create()
            .addDrawable("logo", "@drawable/logo", toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("drawable_tools_ignore.xml", poet)
    }

    @Test
    fun dimensionWithToolsIgnore() {
        val poet = ResourcesPoet.create()
            .addDimension("padding", "16dp", toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("dimen_tools_ignore.xml", poet)
    }

    @Test
    fun integerWithToolsIgnore() {
        val poet = ResourcesPoet.create()
            .addInteger("max_count", 100, toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("integer_tools_ignore.xml", poet)
    }

    @Test
    fun integerArrayWithToolsIgnore() {
        val poet = ResourcesPoet.create()
            .addIntegerArray("numbers", listOf(1, 2, 3), toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("integer_array_tools_ignore.xml", poet)
    }

    @Test
    fun stringArrayWithToolsIgnore() {
        val poet = ResourcesPoet.create()
            .addStringArray("countries", listOf("US", "UK"), toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("string_array_tools_ignore.xml", poet)
    }

    @Test
    fun typedArrayWithToolsIgnore() {
        val poet = ResourcesPoet.create()
            .addTypedArray("refs", listOf("@string/a", "@string/b"), toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("typed_array_tools_ignore.xml", poet)
    }

    @Test
    fun pluralsWithToolsIgnore() {
        val poet = ResourcesPoet.create()
            .addPlurals(
                "songs",
                listOf(
                    Plural(Plural.Quantity.one, "%d song"),
                    Plural(Plural.Quantity.other, "%d songs")
                ),
                toolsIgnore = "UnusedResource"
            )

        TestUtil.assertEquals("plurals_tools_ignore.xml", poet)
    }

    @Test
    fun styleWithToolsIgnore() {
        val poet = ResourcesPoet.create()
            .addStyle("AppTheme", parentRef = "Theme.Base", toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("style_tools_ignore.xml", poet)
    }

    @Test
    fun attrWithToolsIgnore() {
        val formats = ArrayList<Attr.Format>()
        formats.add(Attr.Format.STRING)
        val attr = Attr("font", formats)
        val poet = ResourcesPoet.create()
            .addAttr(attr, toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("attr_tools_ignore.xml", poet)
    }

    @Test
    fun idWithToolsIgnore() {
        val poet = ResourcesPoet.create()
            .addId("my_id", toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("id_tools_ignore.xml", poet)
    }

    @Test
    fun addWithTypeToolsIgnore() {
        val poet = ResourcesPoet.create()
            .add(Type.STRING, "test_key", "test_value", toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("string_add_type_tools_ignore.xml", poet)
    }

    @Test
    fun multipleLintRules() {
        val poet = ResourcesPoet.create()
            .addString("app_name", "Test", toolsIgnore = "UnusedResource,UnusedIds")

        TestUtil.assertEquals("string_multiple_tools_ignore.xml", poet)
    }

    @Test
    fun topLevelToolsIgnore() {
        val poet = ResourcesPoet.create()
            .toolsIgnore("TypographyDashes")
            .addString("somekey", "something-else")

        TestUtil.assertEquals("top_level_tools_ignore.xml", poet)
    }

    @Test
    fun topLevelAndPerElementToolsIgnore() {
        val poet = ResourcesPoet.create()
            .toolsIgnore("UnusedResource")
            .addString("key1", "value1")
            .addString("key2", "value2", toolsIgnore = "UnusedIds")

        TestUtil.assertEquals("top_level_and_per_element.xml", poet)
    }
}
