package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests the resources creation
 */
class PluralsTest {

    @Test
    fun pluralsTest() {
        val plurals = ArrayList<Plural>()
        plurals.add(Plural(Plural.Quantity.one, "%d song"))
        plurals.add(Plural(Plural.Quantity.other, "%d songs"))

        val poet = ResourcesPoet.create()
            .addPlurals("songs", plurals)

        TestUtil.assertEquals("plurals.xml", poet)
    }
}
