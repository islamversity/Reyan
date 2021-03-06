package com.islamversity.surah

import com.islamversity.core.*
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.domain.model.aya.AyaRepoModel
import com.islamversity.domain.model.surah.ReadingBookmarkRepoModel
import com.islamversity.domain.model.surah.SurahID
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.domain.repo.aya.GetAyaUseCase
import com.islamversity.domain.repo.surah.GetSurahUsecase
import com.islamversity.domain.repo.surah.BookmarkAyaUsecase
import com.islamversity.navigation.Navigator
import com.islamversity.navigation.model.StartingAya
import com.islamversity.navigation.model.SurahLocalModel
import com.islamversity.surah.model.AyaUIModel
import com.islamversity.surah.model.SurahHeaderUIModel
import com.islamversity.surah.model.UIItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class SurahProcessor(
    private val navigator: Navigator,
    private val getAyaUseCase: GetAyaUseCase,
    private val ayaMapper: Mapper<AyaRepoModel, AyaUIModel>,
    private val surahRepoHeaderMapper: Mapper<SurahRepoModel, SurahHeaderUIModel>,
    private val settingRepo: SettingRepo,
    private val surahUsecase: GetSurahUsecase,
    private val bookmarkAyaUseCase: BookmarkAyaUsecase,
    private val settingsProcessor: MviProcessor<SurahIntent, SurahResult>
) : BaseProcessor<SurahIntent, SurahResult>() {

    override fun transformers(): List<FlowBlock<SurahIntent, SurahResult>> = listOf(
        processInitial,
        mainAyaFontSizeProcessor,
        translationFontSizeProcessor,
        ayaToolbarVisibleProcessor,
        settingsProcessor.actionProcessor,
        surahSaveState
    )

    private val processInitial: FlowBlock<SurahIntent, SurahResult> = {
        ofType<SurahIntent.Initial>().publish(
            processFullSurah,
            processFullJuz,
        )
    }

    private val processFullSurah: FlowBlock<SurahIntent.Initial, SurahResult> = {
        filter {
            it.localModel is SurahLocalModel.FullSurah
        }
            .map {
                it.localModel as SurahLocalModel.FullSurah
            }
            .flatMapLatest { initial ->
                val id = SurahID(initial.surahID)
                combine(
                    getSurahDetail(id),
                    getSurahAyas(id),
                ) { surah, ayas ->
                    ayas.toMutableList<UIItem>().apply {
                        add(0, surah)
                    }
                }
                    .map {
                        SurahResult.Items(it) to initial.startingFrom
                    }
            }
            .publishAyaAndScroll()
    }

    private val processFullJuz: FlowBlock<SurahIntent.Initial, SurahResult> = {
        filter {
            it.localModel is SurahLocalModel.FullJuz
        }
            .map {
                it.localModel as SurahLocalModel.FullJuz
            }
            .flatMapLatest {
                getAyaUseCase.observeAyaForJuz(it.juzOrder)
                    .map { ayas ->
                        ayas to it
                    }
            }
            .flatMapLatest { ayasAndLocal ->
                val ayas = ayasAndLocal.first
                val allSurahIds = ayas.distinctBy { it.surahId.id }.map { it.surahId }

                surahUsecase.getAll(allSurahIds)
                    .mapListWith(surahRepoHeaderMapper)
                    .map { headers ->
                        val uiList = mutableListOf<UIItem>()
                        var currentHeader = headers.first { it.rowId == ayas.first().surahId.id }
                        uiList.add(currentHeader)

                        for (aya in ayas) {
                            if (aya.surahId.id != currentHeader.rowId) {
                                currentHeader = headers.first { it.rowId == aya.surahId.id }
                                uiList.add(currentHeader)
                            }

                            uiList.add(ayaMapper.map(aya))
                        }

                        SurahResult.Items(
                            uiList as List<UIItem>
                        )
                    }
                    .map { items ->
                        items to ayasAndLocal.second.startingFrom
                    }
                    .publishAyaAndScroll()
            }
    }

    private fun Flow<Pair<SurahResult.Items, StartingAya>>.publishAyaAndScroll(): Flow<SurahResult> = publish(
        {
            //perform showing the chosen aya only the first time
            take(1).showAyasAndScroll()
        },
        {
            //after the first data load we don't need to care about startingAyaPosition
            drop(1).map {
                it.first
            }
        }
    )


    private fun Flow<Pair<SurahResult.Items, StartingAya>>.showAyasAndScroll(): Flow<SurahResult> = transform {
        emit(it.first)
        if (it.second is StartingAya.ID.Beginning) {
            //Screen starts from the top not any number
            return@transform
        }

        val aya = findAya(it.first.items, it.second)

        emit(
            SurahResult.ShowAyaNumber(
                position = it.first.items.indexOf(aya),
                id = aya.rowId,
                orderID = aya.order,
            )
        )
        delay(300)
        emit(SurahResult.LastStable)
    }

    private fun findAya(items: List<UIItem>, startingAya: StartingAya): AyaUIModel {
        return when (startingAya) {
            StartingAya.ID.Beginning -> throw IllegalArgumentException("starting from beginning does not need finding")
            is StartingAya.Order -> {
                items.find { item ->
                    item is AyaUIModel && item.order == startingAya.order
                }
            }
            is StartingAya.ID.AyaId -> {
                items.find { item ->
                    item is AyaUIModel && item.rowId == startingAya.id
                }
            }
        } as? AyaUIModel
            ?: throw error("could not find the starting point $startingAya in the list size of ${items.size}")
    }

    private fun getSurahDetail(id: SurahID): Flow<SurahHeaderUIModel> =
        surahUsecase.getSurah(id)
            .map { repoModel ->
                repoModel ?: error("this screen can not be opened with wrong surahId= ${id.id}")
            }
            .mapWith(surahRepoHeaderMapper)

    private fun getSurahAyas(id: SurahID): Flow<List<AyaUIModel>> =
        getAyaUseCase.observeAyaMain(id)
            .mapListWith(ayaMapper)

    private val mainAyaFontSizeProcessor: FlowBlock<SurahIntent, SurahResult> = {
        ofType<SurahIntent.Initial>()
            .flatMapLatest {
                settingRepo.getAyaMainFontSize().map {
                    SurahResult.MainAyaFontSize(it.size)
                }
            }
    }

    private val translationFontSizeProcessor: FlowBlock<SurahIntent, SurahResult> = {
        ofType<SurahIntent.Initial>()
            .flatMapLatest {
                settingRepo.getAyaTranslateFontSize().map {
                    SurahResult.TranslationFontSize(it.size)
                }
            }
    }

    private val ayaToolbarVisibleProcessor: FlowBlock<SurahIntent, SurahResult> = {
        ofType<SurahIntent.Initial>()
            .flatMapLatest {
                settingRepo.getAyaToolbarVisibility().map {
                    SurahResult.AyaToolbarVisible(it)
                }
            }
    }

    private val surahSaveState: FlowBlock<SurahIntent, SurahResult> = {
        ofType<SurahIntent.Scrolled>()
            .transform {

                val ayaUI = it.uiItems.findFirstAya(it.position) ?: return@transform

                val bookmarkModel = when (it.localModel) {
                    is SurahLocalModel.FullSurah ->
                        createBookmarkFromSurah(ayaUI, it.localModel)
                    is SurahLocalModel.FullJuz -> {
                        val surahHeader = it.uiItems.findFirstSurahHeader(it.position) ?: return@transform

                        createBookmarkFromJuz(surahHeader, ayaUI, it.localModel)
                    }
                }

                bookmarkAyaUseCase.saveBookmark(bookmarkModel)
            }
    }

    private fun createBookmarkFromSurah(
        ayaUIModel: AyaUIModel,
        localModel: SurahLocalModel.FullSurah
    ): ReadingBookmarkRepoModel =
        ReadingBookmarkRepoModel(
            ReadingBookmarkRepoModel.PageType.SURAH,
            null,

            localModel.surahName,
            localModel.surahID,

            ayaUIModel.rowId,
            ayaUIModel.order
        )

    private fun createBookmarkFromJuz(
        surahHeader: SurahHeaderUIModel,
        ayaUIModel: AyaUIModel,
        localModel: SurahLocalModel.FullJuz
    ): ReadingBookmarkRepoModel = ReadingBookmarkRepoModel(
        ReadingBookmarkRepoModel.PageType.JUZ,
        localModel.juzOrder,
        surahHeader.name,
        surahHeader.rowId,
        ayaUIModel.rowId,
        ayaUIModel.order
    )

    private fun List<UIItem>.findFirstAya(position: Int): AyaUIModel? {
        if (position < 0 || position >= size) return null

        val current = get(position)
        if (current is AyaUIModel) {
            return current
        }

        return findFirstAya(position + 1)
    }

    private fun List<UIItem>.findFirstSurahHeader(position: Int): SurahHeaderUIModel? {
        if (position < 0 || position >= size) return null


        val current = get(position)
        if (current is SurahHeaderUIModel) {
            return current
        }

        return findFirstSurahHeader(position - 1)
    }
}
