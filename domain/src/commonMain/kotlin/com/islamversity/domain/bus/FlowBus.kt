package com.islamversity.domain.bus

import com.islamversity.core.ofType
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow

//object FlowBusss {
//
//    private val publisher = Channel<Any>()
//
//    suspend fun publish(data: Any) {
//        publisher.send(data)
//    }
//
//    fun<T> listen(dataType : Class<T>) : Flow<T> = publisher.ofType(eventType)
//}



object FlowBus {

    private val publisher = Channel<Any>()

    fun postEvent(event: Any) {
        publisher.send(event)
    }

    fun<T> observeEvents(dataType : Class<T>): Flow<T> = publisher.receiveAsFlow


    override fun observeEventsOnUiThread(): Flow<Any> {
        return observeEvents()
            .flowOn(MainScope())
    }
}