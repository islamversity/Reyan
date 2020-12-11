
package com.islamversity.core.mvi

import com.islamversity.core.FlowBlock
import com.islamversity.core.Logger
import com.islamversity.core.Severity
import com.islamversity.core.publish
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*

/**
 * Object that will subscribes to a [MviView]'s [MviIntent]s,
 * process it and emit a [MviViewState] back.
 *
 * @param I Top class of the [MviIntent] that the [MviPresenter] will be subscribing
 * to.
 * @param S Top class of the [MviViewState] the [MviPresenter] will be emitting.
 */
interface MviPresenter<I : MviIntent, S : MviViewState> {

    fun processIntents(intents: I)

    //can be collected multipleTimes
    fun receiveStates(): Flow<S>

    //can be collected only once
    fun consumeStates() : Flow<S>

    fun close() {
    }
}

abstract class BasePresenter<I : MviIntent, S : MviViewState, R : MviResult>(
    val processor: MviProcessor<I, R>,
    private val startState: S
) : MviPresenter<I, S> {

    private val presenterJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + presenterJob)

    private val reducer: suspend (S, R) -> S = { preState, result ->
        reduce(preState, result)
    }

    private val intents = MutableSharedFlow<I>(0, 10)
    private val states : Flow<S> = compose()
    private val receiveStates: SharedFlow<S> by lazy {
        compose().shareIn(uiScope, SharingStarted.Eagerly, 1)
    }

    private fun compose(): Flow<S> =
        intents
            .onEach {
                Logger.log(severity = Severity.Info, tag = this@BasePresenter::class.simpleName ?: "BasePresenter") {
                    "NewIntent: $it"
                }
            }
            .publish(filterIntent())
            .let(processor.actionProcessor)
            .scan(startState, { preState, result ->
                reduce(preState, result)
            })
            .distinctUntilChanged()
            .onEach {
                logNewState(it)
            }

    override fun processIntents(intents: I) {
        this.intents.tryEmit(intents)
    }

    override fun receiveStates(): Flow<S> = receiveStates

    override fun consumeStates(): Flow<S> =
        states

    protected open suspend fun logNewState(newState: S) {
        Logger.log(severity = Severity.Info, tag = this@BasePresenter::class.simpleName ?: "BasePresenter") {
            "NewState: $newState"
        }
    }

    protected open fun filterIntent(): List<FlowBlock<I, I>> =
        listOf({
            this
        })

    protected abstract fun reduce(preState: S, result: R): S

    override fun close() {
        presenterJob.cancel()
    }
}