package com.islamversity.core

interface Mapper<T, R>{
    fun map(item : T) : R
    fun listMap(items : List<T>) : List<R>
}

//fun <T, R> listMap(items: List<T>) : R {
//    val mappedItems: MutableList<R> = mutableListOf()
//
//    items.forEach { item ->
//        map(item)?.let { mi ->
//            mappedItems.add(mi)
//        }
//    }
//
//    return mappedItems.toList()
//}