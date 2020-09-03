package com.islamversity.quran_home.feature.surah

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviResult
import com.islamversity.quran_home.feature.surah.model.SurahUIModel

sealed class SurahListResult : MviResult {
    data class Error(val err: BaseState.ErrorState) : SurahListResult()
    object Loading : SurahListResult()
    data class SurahsSuccess(val list : List<SurahUIModel>) : SurahListResult()
}