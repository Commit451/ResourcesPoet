package com.commit451.resourcespoet;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Tests the resources creation
 */
public class IntegerArrayTest {

    @Test
    public void integerArrayTest() throws Exception {
        String text = Util.getFileText("integer_array.xml");

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);

        ResourcesPoet poet = ResourcesPoet.create()
                .addIntegerArray("numbers", numbers);

        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        poet.build(result, true);

        String writtenContent = writer.toString();
        System.out.println(writtenContent);

        Assert.assertEquals(Util.trimtrimtrim(text), Util.trimtrimtrim(writtenContent));
    }
}
