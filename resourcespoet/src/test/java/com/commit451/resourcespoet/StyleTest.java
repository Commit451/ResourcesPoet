package com.commit451.resourcespoet;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Tests the resources creation
 */
public class StyleTest {

    @Test
    public void styleTest() throws Exception {
        ArrayList<StyleItem> styleItems = new ArrayList<>();
        styleItems.add(new StyleItem("android:windowBackground", "@android:color/white"));
        styleItems.add(new StyleItem("colorPrimaryDark", "@android:color/black"));
        ResourcesPoet poet = ResourcesPoet.create()
                .addStyle("AppTheme.Dark", "Base.AppTheme.Dark", styleItems);

        TestUtil.assertEquals("style.xml", poet);
    }
}
