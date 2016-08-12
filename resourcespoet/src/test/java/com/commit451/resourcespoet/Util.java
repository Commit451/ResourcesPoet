package com.commit451.resourcespoet;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;

/**
 * Does some simple things for the tests
 */
class Util {

    static String getFileText(String fileName) throws IOException {
        URL url = Resources.getResource(fileName);
        return Resources.toString(url, Charsets.UTF_8);
    }

    static String trimtrimtrim(String str) {
        return str.trim().replaceAll("\\t", "");
        //return str.trim().replaceAll("\\s", "");
    }
}
