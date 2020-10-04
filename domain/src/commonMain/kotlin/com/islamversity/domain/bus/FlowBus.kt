package com.islamversity.domain.bus

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*


object FlowBus {

    val publisher = Channel<Any>()

    suspend fun postEvent(event: Any) {
        publisher.send(event)
    }

    inline fun <reified T> observeEvents(): Flow<T> = publisher.receiveAsFlow()
        .filter { it is T }
        .map { it as T }


    inline fun <reified T> observeEventsOnUiThread(): Flow<T> = observeEvents<T>()
            .flowOn(Dispatchers.Main)

}