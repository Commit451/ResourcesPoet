package com.commit451.resourcespoet

import org.junit.Test
import java.io.File

/**
 * Tests create(File, indent, elementType)
 */
class CreateFileWithElementTypeTest {

    @Test
    fun createFileWithElementType() {
        val file = File(javaClass.classLoader.getResource("create_file_with_element_type.xml")!!.file)
        val poet = ResourcesPoet.create(file, indent = true)
            .addColor("color_secondary", "#00FF00")

        val result = poet.build()
        assert(result.contains("<color name=\"color_primary\">#FF0000</color>"))
        assert(result.contains("<color name=\"color_secondary\">#00FF00</color>"))
    }

    @Test
    fun createFileWithElementTypeFontFamilies() {
        val file = File(javaClass.classLoader.getResource("fonts.xml")!!.file)
        val poet = ResourcesPoet.create(file, indent = true, elementType = ResourcesPoet.Companion.ELEMENT.FONT_FAMILIES)
            .addFontFamily(FontFamily("italic", "700", "@font/roboto_italic"))

        val result = poet.build()
        assert(result.contains("<font-family"))
        assert(result.contains("@font/lobster_regular"))
        assert(result.contains("@font/roboto_italic"))
    }
}
