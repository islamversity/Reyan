package com.islamversity.core.mvi

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.islamversity.core.FlowBlock
import com.islamversity.core.ofType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Executors
import kotlin.test.Ignore

val initialState = BasePresenterTest.State(false, false)

class BasePresenterTest {


    sealed class Intent : MviIntent {
        object First : Intent()
        object Second : Intent()

    }

    sealed class MVIResult : MviResult {
        object First : MVIResult()
        object Second : MVIResult()
    }

    data class State(
        val first: Boolean,
        val second: Boolean
    ) : MviViewState

    class FakeProcessor : BaseProcessor<Intent, MVIResult>(true) {
        override fun transformers(): List<FlowBlock<Intent, MVIResult>> = listOf(
            {
                ofType<Intent.First>()
                    .map {
                        MVIResult.First
                    }
            },
            {
                ofType<Intent.Second>()
                    .map {
                        MVIResult.Second
                    }
            }
        )
    }

    lateinit var presenter: FakePresenter

    class FakePresenter :
        BasePresenter<Intent, State, MVIResult>(FakeProcessor(), initialState) {
        override fun reduce(preState: State, result: MVIResult): State =
            when (result) {
                MVIResult.First -> preState.copy(first = true, second = false)
                MVIResult.Second -> preState.copy(second = true, first = false)
            }

    }

    private val mainThreadSurrogate = TestCoroutineDispatcher()
//    private val testCoroutineDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    @Before
    fun setup() {

        Dispatchers.setMain(mainThreadSurrogate)
        presenter = FakePresenter()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `first states comes through`(): Unit = runBlocking(mainThreadSurrogate) {

        presenter.states().test {
            presenter.processIntents(Intent.First)
            presenter.processIntents(Intent.Second)

            assertThat(expectItem()).isEqualTo(initialState)
            assertThat(expectItem()).isEqualTo(initialState.copy(first = true))
            assertThat(expectItem()).isEqualTo(initialState.copy(second = true))

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `second collector only gets the last state`() = runBlocking(mainThreadSurrogate) {
        presenter.states().test {
            presenter.processIntents(Intent.First)
            presenter.processIntents(Intent.Second)

            assertThat(expectItem()).isEqualTo(initialState)
            assertThat(expectItem()).isEqualTo(initialState.copy(first = true))
            assertThat(expectItem()).isEqualTo(initialState.copy(second = true))

            cancelAndIgnoreRemainingEvents()
        }



        presenter.states().test {
            assertThat(expectItem()).isEqualTo(initialState.copy(second = true))

            cancelAndIgnoreRemainingEvents()
        }



        Unit
    }

    @Test
    @Ignore
    fun `closing presenter closes all channels and flows`() = runBlocking(mainThreadSurrogate) {
        presenter.processIntents(Intent.First)
        presenter.processIntents(Intent.Second)

        val stateFlow = presenter.states()
        stateFlow.take(1).toList()

        launch {
            presenter.states().collect()
        }

        presenter.close()

//        assertThat(presenter.holder.channelList).isEmpty()

//        assertThat(presenter.intents.isClosedForReceive).isTrue()
//        assertThat(presenter.intents.isClosedForSend).isTrue()

        Unit
    }

}