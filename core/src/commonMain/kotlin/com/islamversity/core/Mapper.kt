package com.islamversity.core

interface Mapper<T, R>{
    fun map(item : T) : R
}

fun <T, R> Mapper<T, R>.listMap(items: List<T>) : List<R> =
    items.map { this.map(it) }
