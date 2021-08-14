package com.islamversity.quran_home.feature.home

import com.islamversity.core.FlowBlock
import com.islamversity.core.Mapper
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.core.ofType
import com.islamversity.domain.model.surah.ReadingBookmarkRepoModel
import com.islamversity.domain.repo.surah.GetBookmarkAyaUsecase
import com.islamversity.navigation.Navigator
import com.islamversity.navigation.Screens
import com.islamversity.navigation.model.SearchLocalModel
import com.islamversity.navigation.model.StartingAya
import com.islamversity.navigation.model.SurahLocalModel
import com.islamversity.navigation.navigateTo
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map

class QuranHomeProcessor(
    navigator: Navigator,
    getBookmarkAyaUsecase: GetBookmarkAyaUsecase,
    surahStateMapper: Mapper<ReadingBookmarkRepoModel, BookmarkState>,
) : BaseProcessor<QuranHomeIntent, QuranHomeResult>() {
    override fun transformers(): List<FlowBlock<QuranHomeIntent, QuranHomeResult>> =
        listOf(loadLastVisitState, lastVisitClick, settingsClicked, searchClicked)

    private val loadLastVisitState: FlowBlock<QuranHomeIntent, QuranHomeResult> = {
        ofType<QuranHomeIntent.Initial>()
            .flatMapMerge {
                getBookmarkAyaUsecase.getBookmarkAya()
            }
            .ofType<ReadingBookmarkRepoModel>()
            .map { bookmark ->
                QuranHomeResult.LastBookmarkAya(bookmarkAya = surahStateMapper.map(bookmark))
            }
    }

    private val searchClicked: FlowBlock<QuranHomeIntent, QuranHomeResult> = {
        ofType<QuranHomeIntent.SearchClicked>()
            .map {
                Screens.Search(SearchLocalModel())
            }
            .navigateTo(navigator)
    }

    private val settingsClicked: FlowBlock<QuranHomeIntent, QuranHomeResult> = {
        ofType<QuranHomeIntent.SettingsClicked>()
            .map {
                Screens.Settings()
            }
            .navigateTo(navigator)
    }

    private val lastVisitClick: FlowBlock<QuranHomeIntent, QuranHomeResult> = {
        ofType<QuranHomeIntent.LastVisitClicked>()
            .map { it.state }
            .map {
                when (it.pageType) {
                    BookmarkState.PageType.SURAH -> SurahLocalModel.FullSurah(
                        it.surahName,
                        it.surahId,
                        StartingAya.ID.AyaId(it.ayaId)
                    )
                    BookmarkState.PageType.JUZ -> SurahLocalModel.FullJuz(
                        it.juz ?: error("bookmark saved for juz screen but juz is null, $it"),
                        StartingAya.ID.AyaId(it.ayaId)
                    )
                }
            }
            .map { Screens.Surah(it) }
            .navigateTo(navigator)
    }
}