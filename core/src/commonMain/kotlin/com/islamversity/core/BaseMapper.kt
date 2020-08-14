package com.islamversity.core

abstract class BaseMapper<T, R> : Mapper<T, R> {

    override fun listMap(items: List<T>): List<R> {

        val mappedItems: MutableList<R> = mutableListOf()

        items.map { item ->
            map(item)?.let { mi ->
                mappedItems.add(mi)
            }
        }

        return mappedItems.toList()
    }
}