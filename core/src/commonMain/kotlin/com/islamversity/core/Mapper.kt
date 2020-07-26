package com.islamversity.core

interface Mapper<T, R>{
    fun map(item : T) : R
}