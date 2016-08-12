package com.commit451.resourcespoet;

import java.util.ArrayList;
import java.util.List;

/**
 * Silly utils
 */
class Util {

    public static List<String> from(List<Integer> integers) {
        ArrayList<String> values = new ArrayList<>();
        for (Integer value : integers) {
            values.add(String.valueOf(value));
        }
        return values;
    }
}
