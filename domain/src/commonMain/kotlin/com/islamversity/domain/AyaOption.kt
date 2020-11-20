package com.islamversity.domain

inline class AyaOption(val enable : Boolean){
    companion object {
        val DEFAULT = AyaOption(false)
    }

    fun String.toBoolean(){
        equals("true",true)
    }
}