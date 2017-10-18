package com.commit451.resourcespoet

import org.junit.Test
import java.util.*

/**
 * Tests the resources creation
 */
class AttrTest {

    @Test
    fun attrTest() {
        val formats = ArrayList<Attr.Format>()
        formats.add(Attr.Format.STRING)
        formats.add(Attr.Format.REFERENCE)
        val attr = Attr("font", formats)
        val poet = ResourcesPoet.create()
                .addAttr(attr)

        TestUtil.assertEquals("attr.xml", poet)
    }
}
