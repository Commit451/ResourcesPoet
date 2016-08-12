package com.commit451.resourcespoet.sample;

import com.commit451.resourcespoet.ResourcesPoet;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Sample which takes translations from a json source and converts them to appropriate resources files for Android
 */
public class Main {

    public static void main(String[] args) {

        Moshi moshi = new Moshi.Builder()
                .build();

        Type listOfStatesType = Types.newParameterizedType(List.class, State.class);
        JsonAdapter<List<State>> jsonAdapter = moshi.adapter(listOfStatesType);

        try {
            URL url = Resources.getResource("states.json");
            String statesJson = Resources.toString(url, Charsets.UTF_8);
            List<State> states = jsonAdapter.fromJson(statesJson);
            ResourcesPoet poet = ResourcesPoet.create();
            List<String> stateStrings = new ArrayList<>();
            for (State state : states) {
                stateStrings.add(state.name);
            }
            poet.addStringArray("states", stateStrings);

            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            poet.build(result, true);

            String resourcesXml = writer.toString();
            System.out.println(resourcesXml);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
