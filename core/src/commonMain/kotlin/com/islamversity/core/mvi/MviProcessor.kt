package com.islamversity.core.mvi

import com.islamversity.core.FlowBlock
import com.islamversity.core.Logger
import com.islamversity.core.Severity
import com.islamversity.core.publish
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

interface MviProcessor<A : MviIntent, R : MviResult> {

    val actionProcessor: (Flow<A>) -> Flow<R>
}

abstract class BaseProcessor<A : MviIntent, R : MviResult>(
    private val logIntents: Boolean,
) : MviProcessor<A, R> {

    constructor() : this(false)

    final override val actionProcessor: (Flow<A>) -> Flow<R> = {
        val blocks = transformers()
        val loggingBlocks = if (logIntents) blocks + intentLogger else blocks
        it.publish(loggingBlocks)
    }

    protected abstract fun transformers(): List<FlowBlock<A, R>>

    private val intentLogger: FlowBlock<A, R> = {
        onEach {
            Logger.log(
                severity = Severity.Info,
                tag = this@BaseProcessor::class.simpleName ?: "DefaultProcessor"
            ) {  it.toString() }
        }
            .catch {
                Logger.log(
                    severity = Severity.Error,
                    tag = this@BaseProcessor::class.simpleName ?: "DefaultProcessor",
                    throwable = it
                ) { "" }
            }.transform {}
    }
}