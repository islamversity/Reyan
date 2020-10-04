package com.islamversity.domain.bus

import com.islamversity.core.ofType
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.filter
import kotlinx.coroutines.channels.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlin.reflect.KClass

//
//interface EventBus {
//    fun postEvent(event: Any)
//    fun<T> observeEvents(dataType : Class<T>): Flow<T>
//    fun observeEventsOnUiThread(): Flow<Any>
//
//    fun<T> listen() : Flow<T> = publisher.ofType(eventType)
//}

//
//inline fun <reified T : Any> EventBus.observeEvent(): Flow<T> {
//    return observeEvents().ofType()
//}
//
//inline fun <reified T : Any> EventBus.observeEventsOnUiThread(): Flow<T> {
//    return observeEventsOnUiThread().ofType()
//}
//
//
//object EventBus {
//
//    val bus: BroadcastChannel<Any> = BroadcastChannel(10)
//
//    suspend fun send(event: Any) {
//        bus.send(event)
//    }
//
//    fun<T> subscribe(dataType : KClass<T>): Flow<T> = bus.asFlow()
//
//
//
//
//
//
//
//
//
//    inline fun <reified T> subscribeToEvent() =
//        subscribe().let {
//            produce<T>(coroutineContext) {
//                for (t in it){
//                    if(t is T)
//                        send(t as T)
//                }
//            }
//
//        }
//
//
//    inline fun <reified T> asChannel(): ReceiveChannel<T> {
//        return bus.openSubscription().filter { it is T }.map { it as T }
//    }
//}
//// To post an event:
//EventBus.send(SomeEvent())
//
//
//// To subscribe to an event:
//var subscription = EventBus.asChannel<SomeEvent>()
//launch(UI) {
//    subscription.consumeEach { event ->
//        // Handles the event...
//    }
//}
//
//// To cancel a subscriber:
//subscription.cancel()




import com.islamversity.core.ofType
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.map
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

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