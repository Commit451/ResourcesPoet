package com.commit451.resourcespoet;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Tests the resources creation
 */
public class StyleTest {

    @Test
    public void styleTest() throws Exception {
        String text = Util.getFileText("style.xml");

        ArrayList<StyleItem> styleItems = new ArrayList<>();
        styleItems.add(new StyleItem("android:windowBackground", "@android:color/white"));
        styleItems.add(new StyleItem("colorPrimaryDark", "@android:color/black"));
        ResourcesPoet poet = ResourcesPoet.create()
                .addStyle("AppTheme.Dark", "Base.AppTheme.Dark", styleItems);

        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        poet.build(result, true);

        String writtenContent = writer.toString();
        System.out.println(writtenContent);

        Assert.assertEquals(Util.trimtrimtrim(text), Util.trimtrimtrim(writtenContent));
    }
}
