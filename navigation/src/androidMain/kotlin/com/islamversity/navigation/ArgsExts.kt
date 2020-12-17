package com.islamversity.navigation

import com.bluelinelabs.conductor.Controller
import kotlinx.serialization.decodeFromString

@Suppress("UNCHECKED_CAST")
inline fun <reified T> Controller.requireArgs(key : String) : T {
    return jsonParser.decodeFromString(args.getString(key)!!)
}
