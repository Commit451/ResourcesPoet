package com.commit451.resourcespoet

import com.google.common.base.Charsets
import com.google.common.io.Resources

/**
 * Does some simple things for the tests
 */
internal object Util {

    fun getFileText(fileName: String): String {
        val url = Resources.getResource(fileName)
        return Resources.toString(url, Charsets.UTF_8)
    }

    fun trimtrimtrim(str: String): String {
        return str.trim { it <= ' ' }.replace("\\t".toRegex(), "")
    }
}
