package com.commit451.resourcespoet.sample

import com.commit451.resourcespoet.ResourcesPoet
import com.google.common.base.Charsets
import com.google.common.io.Resources
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.util.*

/**
 * Sample which takes states from a json source and converts them to appropriate resources files for Android
 */
object Main {

    @JvmStatic
    fun main(args: Array<String>) {

        val moshi = Moshi.Builder()
                .build()

        val listOfStatesType = Types.newParameterizedType(List::class.java, State::class.java)
        val jsonAdapter = moshi.adapter<List<State>>(listOfStatesType)

        val url = Resources.getResource("states.json")
        val statesJson = Resources.toString(url, Charsets.UTF_8)
        val states = jsonAdapter.fromJson(statesJson)

        val stateStrings = ArrayList<String>()
        val stateCodes = ArrayList<String>()
        for (state in states) {
            stateStrings.add(state.name!!)
            stateCodes.add(state.abbreviation!!)
        }
        val poet = ResourcesPoet.create()
                .addStringArray("states", stateStrings)
                .addStringArray("state_codes", stateCodes)
                .indent(true)

        val xml = poet.build()

        println(xml)
    }
}
