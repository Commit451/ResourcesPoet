package com.commit451.resourcespoet.sample

import com.squareup.moshi.Json

/**
 * Defines a state from json
 */
class State {

    @Json(name = "name")
    var name: String? = null
    @Json(name = "abbreviation")
    var abbreviation: String? = null
}
