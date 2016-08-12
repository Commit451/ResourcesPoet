package com.commit451.resourcespoet.sample;

import com.squareup.moshi.Json;

/**
 * Defines a state from json
 */
public class State {

    @Json(name = "name")
    public String name;
    @Json(name = "abbreviation")
    public String abbreviation;

    private State() {

    }
}
