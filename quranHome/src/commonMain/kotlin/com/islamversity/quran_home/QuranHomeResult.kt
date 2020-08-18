package com.islamversity.quran_home

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviResult
import com.islamversity.quran_home.model.JozUIModel
import com.islamversity.quran_home.model.SurahUIModel

sealed class QuranHomeResult : MviResult {
    data class Error(val err: BaseState.ErrorState) : QuranHomeResult()
    object Loading : QuranHomeResult()

    data class SurahsSuccess(val list : List<SurahUIModel>) : QuranHomeResult()
    data class JuzSuccess(val list : List<JozUIModel>) : QuranHomeResult()
}