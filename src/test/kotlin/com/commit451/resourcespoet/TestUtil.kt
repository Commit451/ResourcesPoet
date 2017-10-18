package com.commit451.resourcespoet

import org.junit.Assert

/**
 * Helps with tests
 */
object TestUtil {

    fun assertEquals(xmlFileName: String, poet: ResourcesPoet) {
        poet.indent(true)
        val text = Util.getFileText(xmlFileName)

        val writtenContent = poet.build()
        println(writtenContent)

        Assert.assertEquals(Util.trimtrimtrim(text), Util.trimtrimtrim(writtenContent))
    }
}
