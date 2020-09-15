package com.islamversity.core

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Kermit
import co.touchlab.kermit.Logger
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

private val initLogger = init(listOf(CommonLogger()))

object Logger {

    private lateinit var logger: Kermit

    private const val defaultTag = "KLog"

    fun init(loggers: List<Logger>) {
        logger = Kermit(loggers)
    }

    fun log(
        severity: Severity = Severity.Info,
        tag: String? = null,
        throwable: Throwable? = null,
        message: () -> String
    ) {
        logger.log(
            severity = severity.toKermit(),
            tag = tag ?: defaultTag,
            throwable = throwable,
            message = message
        )
    }

    fun log(
        severity: Severity = Severity.Info,
        tag: String? = null,
        throwable: Throwable? = null,
        message: String
    ) {
        log(severity, tag, throwable) { message }
    }
}