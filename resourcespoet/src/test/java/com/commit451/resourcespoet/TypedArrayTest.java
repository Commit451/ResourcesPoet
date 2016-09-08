package com.commit451.resourcespoet;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Tests the resources creation
 */
public class TypedArrayTest {

    @Test
    public void typedArrayTest() throws Exception {
        String text = Util.getFileText("typed_array.xml");

        ArrayList<String> strings = new ArrayList<>();
        strings.add("One");
        strings.add("Two");

        ResourcesPoet poet = ResourcesPoet.create()
                .addTypedArray("some_typed_array", strings);

        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        poet.build(result, true);

        String writtenContent = writer.toString();
        System.out.println(writtenContent);

        Assert.assertEquals(Util.trimtrimtrim(text), Util.trimtrimtrim(writtenContent));
    }
}
