package com.islamversity.quran_home.feature.home

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviResult

sealed class QuranHomeResult : MviResult {
    data class Error(val err: BaseState.ErrorState) : QuranHomeResult()
    data class Success(val tabPosition: Int) : QuranHomeResult()

    object Loading : QuranHomeResult()
}