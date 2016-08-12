package com.commit451.resourcespoet;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.net.URL;

/**
 * Tests the resources creation
 */
public class CreationTest {

    @Test
    public void creationTest() throws Exception {
        URL url = Resources.getResource("config.xml");
        String text = Resources.toString(url, Charsets.UTF_8);

        ResourcesPoet poet = ResourcesPoet.create();
        poet.addString("app_name", "Test");
        poet.addColor("color_primary", "#FF0000");
        poet.addBool("is_cool", true);
        poet.addComment("This is a comment");

        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        poet.build(result, true);

        String writtenContent = writer.toString();

        Assert.assertEquals(text.trim().replaceAll("\\s", ""), writtenContent.trim().replaceAll("\\s", ""));

    }
}
