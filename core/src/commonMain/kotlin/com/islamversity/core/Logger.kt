package com.islamversity.core

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Kermit
import co.touchlab.kermit.Logger
import co.touchlab.stately.concurrency.AtomicReference
import co.touchlab.kermit.Severity as KSeverity

typealias Logger = Printer

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

object Printer {

    private val logger = AtomicReference(Kermit(listOf(CommonLogger())))

    private const val defaultTag = "KLog"

    fun init(loggers: List<Logger>) {
        logger.set(Kermit(loggers))
    }

    fun log(message: String) {
        log(message, Severity.Info, tag = null, throwable = null)
    }

    fun log(message : String, throwable: Throwable) {
        log(message, Severity.Info, tag = null, throwable = throwable)
    }

    fun log(message : String, tag: String) {
        log(message, Severity.Info, tag = tag, throwable = null)
    }

    fun log(message : String, severity: Severity) {
        log(message, severity, tag = null, throwable = null)
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
