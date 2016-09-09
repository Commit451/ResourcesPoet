package com.commit451.resourcespoet;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Tests the resources creation
 */
public class AttrTest {

    @Test
    public void attrTest() throws Exception {
        ArrayList<Attr.Format> formats = new ArrayList<>();
        formats.add(Attr.Format.STRING);
        formats.add(Attr.Format.REFERENCE);
        Attr attr = new Attr("font", formats);
        ResourcesPoet poet = ResourcesPoet.create()
                .addAttr(attr);

        TestUtil.assertEquals("attr.xml", poet);
    }
}
