package com.islamversity.db.model

sealed class SajdahType (
    val name : String
){
    data class None(val calligraphyName  : String) : SajdahType(calligraphyName)
    data class Recommended(val calligraphyName  : String) : SajdahType(calligraphyName)
    data class Obligatory(val calligraphyName  : String) : SajdahType(calligraphyName)

    companion object{
        operator fun invoke(flag : SajdahTypeFlag, name : String) : SajdahType =
            when (flag) {
                SajdahTypeFlag.NONE -> None(name)
                SajdahTypeFlag.RECOMMENDED -> Recommended(name)
                SajdahTypeFlag.OBLIGATORY -> Obligatory(name)
            }

    }
}