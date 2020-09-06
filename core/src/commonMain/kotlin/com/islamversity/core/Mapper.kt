package com.islamversity.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface Mapper<T, R>{
    fun map(item : T) : R
}

fun <T, R> Mapper<T, R>.listMap(items: List<T>) : List<R> =
    items.map { this.map(it) }

fun <T, R> Flow<T>.mapWith(mapper : Mapper<T, R>) : Flow<R> =
    map {
        mapper.map(it)
    }

fun <T, R> Flow<List<T>>.mapListWith(mapper : Mapper<T, R>) : Flow<List<R>> =
    map {
        mapper.listMap(it)
    }