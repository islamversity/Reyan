package com.islamversity.domain.bus

import com.islamversity.core.ofType
import kotlinx.coroutines.flow.Flow

interface EventBus {
    fun postEvent(event: Any)
    fun<T> observeEvents(dataType : Class<T>): Flow<T>
    fun observeEventsOnUiThread(): Flow<Any>

    fun<T> listen() : Flow<T> = publisher.ofType(eventType)
}


inline fun <reified T : Any> EventBus.observeEvent(): Flow<T> {
    return observeEvents().ofType()
}

inline fun <reified T : Any> EventBus.observeEventsOnUiThread(): Flow<T> {
    return observeEventsOnUiThread().ofType()
}
