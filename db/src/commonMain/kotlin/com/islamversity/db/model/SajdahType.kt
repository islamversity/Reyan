package com.islamversity.db.model

sealed class SajdahType (
    val name : String
){
    object None : SajdahType("")
    data class Recommended(val calligraphyName  : String) : SajdahType(calligraphyName)
    data class Obligatory(val calligraphyName  : String) : SajdahType(calligraphyName)

    companion object{
        operator fun invoke(flag : SajdahTypeFlag, name : String?) : SajdahType =
            when (flag) {
                SajdahTypeFlag.NONE -> None
                SajdahTypeFlag.RECOMMENDED -> Recommended(name!!)
                SajdahTypeFlag.OBLIGATORY -> Obligatory(name!!)
            }

    }
}