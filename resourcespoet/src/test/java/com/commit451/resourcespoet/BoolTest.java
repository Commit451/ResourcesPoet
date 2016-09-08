package com.commit451.resourcespoet;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * Tests the resources creation
 */
public class BoolTest {

    @Test
    public void boolTest() throws Exception {
        String text = Util.getFileText("bool.xml");

        ResourcesPoet poet = ResourcesPoet.create()
                .addBool("is_cool", true);

        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        poet.build(result, true);

        String writtenContent = writer.toString();
        System.out.println(writtenContent);

        Assert.assertEquals(Util.trimtrimtrim(text), Util.trimtrimtrim(writtenContent));
    }
}
