package com.commit451.resourcespoet

internal object Util {

    fun getFileText(fileName: String): String {
        val url = checkNotNull(Util::class.java.classLoader.getResource(fileName)) {
            "Resource not found: $fileName"
        }
        return url.readText(Charsets.UTF_8)
    }

    fun trimtrimtrim(str: String): String {
        return str.lines()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .joinToString("\n")
    }
}
