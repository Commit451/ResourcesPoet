package com.commit451.resourcespoet;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Tests the resources creation
 */
public class PluralsTest {

    @Test
    public void pluralsTest() throws Exception {
        String text = Util.getFileText("plurals.xml");

        ArrayList<Plural> plurals = new ArrayList<>();
        plurals.add(new Plural(Plural.Quantity.one, "%d song"));
        plurals.add(new Plural(Plural.Quantity.other, "%d songs"));

        ResourcesPoet poet = ResourcesPoet.create()
                .addPlurals("songs", plurals);

        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        poet.build(result, true);

        String writtenContent = writer.toString();
        System.out.println(writtenContent);

        Assert.assertEquals(Util.trimtrimtrim(text), Util.trimtrimtrim(writtenContent));
    }
}
