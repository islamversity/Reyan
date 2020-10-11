package com.islamversity.nativeShared

interface IWithDefaultParameter {

    fun withDefault(par : String = "")

    fun overloaded() = withDefault("")
}

class WithDefaultParameterImpl : IWithDefaultParameter{
    override fun withDefault(par: String) {
        TODO("Not yet implemented")
    }

    fun second() = withDefault("")
}