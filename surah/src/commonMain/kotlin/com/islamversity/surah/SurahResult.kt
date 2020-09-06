package com.islamversity.surah

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviResult
import com.islamversity.surah.model.AyaUIModel

sealed class SurahResult : MviResult {
    object LastStable : SurahResult()
    object Loading : SurahResult()
    data class Error(val err: BaseState.ErrorState) : SurahResult()

    data class MainAyasLoaded(
        val ayas: List<AyaUIModel>,
        val startFrom: Int,
        val fontSize: Int
    ) : SurahResult()

    sealed class Bismillah : SurahResult(){
        object Hide : Bismillah()
        object Show : Bismillah()
        data class Content(val value : String) : Bismillah()
    }
}