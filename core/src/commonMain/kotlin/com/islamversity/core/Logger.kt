package com.islamversity.core

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Kermit
import co.touchlab.kermit.Logger
import co.touchlab.stately.concurrency.AtomicReference
import com.islamversity.core.Logger.init
import co.touchlab.kermit.Severity as KSeverity

enum class Severity {
    Verbose,
    Debug,
    Info,
    Warn,
    Error,
    Assert,
    ;


    internal fun toKermit() = when (this) {
        Verbose -> KSeverity.Verbose
        Debug -> KSeverity.Debug
        Info -> KSeverity.Info
        Warn -> KSeverity.Warn
        Error -> KSeverity.Error
        Assert -> KSeverity.Assert
    }
}

object Logger {

    private val logger = AtomicReference(Kermit(listOf(CommonLogger())))

    private const val defaultTag = "KLog"

    fun init(loggers: List<Logger>) {
        logger.set(Kermit(loggers))
    }

    fun log(
        severity: Severity = Severity.Info,
        tag: String? = null,
        throwable: Throwable? = null,
        message: () -> String
    ) {
        logger.get().log(
            severity = severity.toKermit(),
            tag = tag ?: defaultTag,
            throwable = throwable,
            message = message
        )
    }

    fun log(
        message: String,
        severity: Severity = Severity.Info,
        tag: String? = null,
        throwable: Throwable? = null,
    ) {
        log(severity, tag, throwable) { message }
    }
}
