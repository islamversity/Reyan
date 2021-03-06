package com.islamversity.quran_home.feature.home

import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.BaseViewState

data class QuranHomeState(
    override val base: BaseState,
    val bookmarkState: BookmarkState? = null,
) : BaseViewState {
    companion object {
        fun idle() =
            QuranHomeState(
                base = BaseState.stable(),
                bookmarkState = null,
            )
    }
}


data class BookmarkState(
    val pageType: PageType,

    val juz : Long?,

    val surahId : String,
    val surahName : String,

    val ayaId : String,
    val ayaOrder : Long

){
    enum class PageType{
        SURAH,
        JUZ,

        ;
    }
}
