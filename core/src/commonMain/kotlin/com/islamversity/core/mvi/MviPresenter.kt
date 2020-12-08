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

    fun states(): Flow<S>

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
    private val states: SharedFlow<S> = compose()

    private fun compose(): SharedFlow<S> =
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
            .shareIn(uiScope, SharingStarted.Eagerly, 1)

    override fun processIntents(intents: I) {
            this.intents.tryEmit(intents)
    }

    override fun states(): Flow<S> = states

    protected open suspend fun logNewState(newState: S) {
        Logger.log(severity = Severity.Info, tag = this@BasePresenter::class.simpleName ?: "BasePresenter") {
            "NewState: $newState"
        }
    override fun states(): Flow<S> = holder.receiveStates()
        .onEach {
//            Logger.log {
//                "BasePresenter : logNewState : newState = "  + it.toString()
//            }
        }

    protected open suspend fun logNewState(newState: S) {

//        Logger.log {
//            "BasePresenter : logNewState : newState = "  + newState.toString()
//        }
    }

    protected open fun filterIntent(): List<FlowBlock<I, I>> =
        listOf({
            this
        })

    protected abstract fun reduce(preState: S, result: R): S

    override fun close() {
        presenterJob.cancel()
    }


    class StatePublisher<S : MviViewState>(
        private var latestState: S,
        private val scope: CoroutineScope
    ) {

        private val lock = Mutex()
        private val channelList = mutableListOf<Channel<S>>()

        fun start(states: Flow<S>) {
            states
                .onEach {
                    Logger.log {
                        "BasePresenter : StatePublisher : state : $it"
                    }
                    lock.withLock {
                        latestState = it
                        signalListeners()
                    }
                }
                .onCompletion {
                    closeAllChannels()
                }
                .launchIn(scope)
        }

        private fun closeAllChannels() {
            channelList.toList().forEach { channel ->
                channelList.remove(channel)
                channel.cancel()
            }
        }

        private fun signalListeners() {
            channelList.forEach { it.offer(latestState) }
        }

        fun receiveStates(): Flow<S> {
            val newChannel = Channel<S>(Channel.UNLIMITED)
            channelList += newChannel

            return newChannel.receiveAsFlow()
                .onCompletion {
                    if (channelList.remove(newChannel)) {
                        newChannel.cancel()
                    }
                    Logger.log {
                        "BasePresenter : StatePublisher : checked closed : $newChannel"
                    }
                    println("checked closed $newChannel")
                }.also {
                    newChannel.offer(latestState)
                }
        }

        fun close() {
            closeAllChannels()
        }
    }

}
