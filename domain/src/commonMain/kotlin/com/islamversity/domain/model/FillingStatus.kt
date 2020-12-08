package com.islamversity.domain.model

sealed class FillingStatus {
    object Idle : FillingStatus()

    object Started : FillingStatus()

    sealed class Filling(val percent: Int) : FillingStatus() {
        object CalligraphiesFilled : Filling(10)
        data class Filled(val total: Int) : Filling(total)
    }

    object Done : FillingStatus()
}