package com.islamversity.core

import app.cash.turbine.test
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.test.Test
import kotlin.test.assertEquals

class FlowPublishTest {

    @Test
    fun willEmitAllItemsOfBothBranches() = suspendTest {
        flowOf(1)
            .publish(
                {
                    map { it.toString() }
                }, {
                    map { (it + 1).toString() }
                }
            ).test {
                assertEquals(expectItem(), "1")
                assertEquals(expectItem(), "2")

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun willCancelTheLastRequestAndSubmitsANewOne() = suspendTest {
        flowOf(1, 2)
            .publish(
                {
                    flatMapLatest {
                        if (it == 1) {
                            flowOf(it)
                                .onEach {
                                    delay(1000)
                                }
                        } else {
                            flowOf(it)
                        }
                    }
                }
            ).test {
                assertEquals(expectItem(), 2)
                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun oneBranchErrorsTheWholeFlowCancels() = suspendTest {
        flowOf(1, 2)
            .publish(
                {
                    map {
                        it.toString()
                    }
                },
                {
                    flow {
                        throw NullPointerException()
                    }
                }
            ).test {
                assertThrows<NullPointerException> {
                    throw expectError()
                }
            }
    }
}
