package com.islamversity.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.DelayController
import kotlinx.coroutines.test.TestCoroutineScope
import kotlin.time.Duration
import kotlin.time.milliseconds

actual fun suspendTest(body: suspend CoroutineScope.() -> Unit) {
    runBlocking { body() }
}

fun jvmSuspendTest(body: suspend TestCoroutineScope.() -> Unit) {
    // We don't use runBlockingTest because it always advances time unconditionally.

    val scope = TestCoroutineScope()
    scope.launch {
        scope.body()
    }
    scope.cleanupTestCoroutines()
}

fun DelayController.advanceTimeBy(duration: Duration): Duration {
    return advanceTimeBy(duration.toLongMilliseconds()).milliseconds
}