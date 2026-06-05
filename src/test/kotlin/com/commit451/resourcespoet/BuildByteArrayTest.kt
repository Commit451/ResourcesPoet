package com.commit451.resourcespoet

import org.junit.Test
import java.nio.charset.StandardCharsets

/**
 * Tests buildBytes() returning a valid UTF-8 ByteArray
 */
class BuildByteArrayTest {

    @Test
    fun buildBytesReturnsValidXml() {
        val poet = ResourcesPoet.create()
            .addString("app_name", "Test")

        val bytes = poet.buildBytes()
        val xml = String(bytes, StandardCharsets.UTF_8)

        assert(xml.contains("<?xml"))
        assert(xml.contains("<resources>"))
        assert(xml.contains("<string name=\"app_name\">Test</string>"))
    }

    @Test
    fun buildBytesContainsUtf8Encoding() {
        val poet = ResourcesPoet.create()
            .addString("test", "Test")

        val bytes = poet.buildBytes()
        val xml = String(bytes, StandardCharsets.UTF_8)

        assert(xml.contains("encoding=\"utf-8\""))
    }
}
