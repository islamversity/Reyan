package com.islamversity.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

actual fun suspendTest(body: suspend CoroutineScope.() -> Unit): dynamic {
    return GlobalScope.promise { body() }
}