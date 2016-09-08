package com.commit451.resourcespoet;

import org.junit.Assert;

/**
 * Helps with tests
 */
public class TestUtil {

    public static void assertEquals(String xmlFileName, ResourcesPoet poet) throws Exception {
        poet.indent(true);
        String text = Util.getFileText(xmlFileName);

        String writtenContent = poet.build();
        System.out.println(writtenContent);

        Assert.assertEquals(Util.trimtrimtrim(text), Util.trimtrimtrim(writtenContent));
    }
}
