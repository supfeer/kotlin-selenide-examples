package ee.found.extensions

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser

fun String.pretty(): String? =
    kotlin.runCatching {
        GsonBuilder().setPrettyPrinting().create().toJson(JsonParser.parseString(this))
    }.getOrElse { this }
