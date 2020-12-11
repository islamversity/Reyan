package com.islamversity.nativeShared

import com.islamversity.core.mvi.MviIntent
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.core.mvi.MviViewState
import com.islamversity.nativeShared.flowutils.Closeable
import com.islamversity.nativeShared.flowutils.wrap

fun <I : MviIntent, S : MviViewState> MviPresenter<I, S>.consume(call: (S) -> Unit): Closeable =
    consumeStates().wrap().watch(call)
