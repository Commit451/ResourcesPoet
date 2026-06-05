package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests int-quantity resource creation
 */
class IntQuantityTest {

    @Test
    fun intQuantityTest() {
        val quantities = listOf(
            Quantity(Plural.Quantity.one, 1),
            Quantity(Plural.Quantity.other, 0)
        )
        val poet = ResourcesPoet.create()
            .addIntQuantity("count", quantities)

        TestUtil.assertEquals("int_quantity.xml", poet)
    }

    @Test
    fun intQuantityWithToolsIgnore() {
        val quantities = listOf(
            Quantity(Plural.Quantity.one, 1),
            Quantity(Plural.Quantity.other, 0)
        )
        val poet = ResourcesPoet.create()
            .addIntQuantity("count", quantities, toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("int_quantity_tools_ignore.xml", poet)
    }

    @Test
    fun intQuantityWithMultipleQuantities() {
        val quantities = listOf(
            Quantity(Plural.Quantity.zero, 0),
            Quantity(Plural.Quantity.one, 1),
            Quantity(Plural.Quantity.two, 2),
            Quantity(Plural.Quantity.few, 3),
            Quantity(Plural.Quantity.many, 10),
            Quantity(Plural.Quantity.other, 100)
        )
        val poet = ResourcesPoet.create()
            .addIntQuantity("count_all", quantities)

        TestUtil.assertEquals("int_quantity_all.xml", poet)
    }
}
