package com.andreashdez.utils

import java.util.*

class FileUtils {

    fun getConfigProperty(key: String): String? {
        val props = javaClass.classLoader.getResourceAsStream("config.properties").use {
            Properties().apply { load(it) }
        }
        return props.getProperty(key)
    }

    fun getConfigPropertyAsInt(key: String, defaultValue: Int): Int {
        return getConfigProperty(key)?.toIntOrNull() ?: defaultValue
    }

}
