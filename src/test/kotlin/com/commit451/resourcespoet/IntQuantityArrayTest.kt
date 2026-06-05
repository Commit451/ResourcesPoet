package com.commit451.resourcespoet

import org.junit.Test

/**
 * Tests int-quantity-array resource creation
 */
class IntQuantityArrayTest {

    @Test
    fun intQuantityArrayTest() {
        val quantities = listOf(
            Quantity(Plural.Quantity.one, 1),
            Quantity(Plural.Quantity.other, 0)
        )
        val poet = ResourcesPoet.create()
            .addIntQuantityArray("counts", quantities)

        TestUtil.assertEquals("int_quantity_array.xml", poet)
    }

    @Test
    fun intQuantityArrayWithToolsIgnore() {
        val quantities = listOf(
            Quantity(Plural.Quantity.one, 1),
            Quantity(Plural.Quantity.other, 0)
        )
        val poet = ResourcesPoet.create()
            .addIntQuantityArray("counts", quantities, toolsIgnore = "UnusedResource")

        TestUtil.assertEquals("int_quantity_array_tools_ignore.xml", poet)
    }
}
