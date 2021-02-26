package com.islamversity.surah

import com.islamversity.core.*
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.domain.model.aya.AyaRepoModel
import com.islamversity.domain.model.surah.SurahID
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.model.surah.SurahStateRepoModel
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.domain.repo.aya.GetAyaUseCase
import com.islamversity.domain.repo.surah.GetSurahUsecase
import com.islamversity.domain.repo.surah.SaveSurahStateUsecase
import com.islamversity.navigation.Navigator
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
    private val surahStateMapper: Mapper<SurahLocalModel.FullSurah, SurahStateRepoModel>,
    private val settingRepo: SettingRepo,
    private val surahUsecase: GetSurahUsecase,
    private val saveSurahStateUseCase: SaveSurahStateUsecase,
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
                        SurahResult.Items(it) to initial
                    }
            }
            .publish(
                {
                    //perform showing the chosen aya only the first time
                    take(1).transform {
                        emit(it.first)
                        if (it.second.startingAyaOrder == 0L) {
                            //Screen starts from the top not any number
                            return@transform
                        }

                        val aya = it.first.items.find { item ->
                            item is AyaUIModel && item.order == it.second.startingAyaOrder
                        }
                            ?: error("The surah does not contain aya with order= ${it.second.startingAyaOrder}")

                        emit(
                            SurahResult.ShowAyaNumber(
                                position = it.first.items.indexOf(aya),
                                id = aya.rowId,
                                orderID = it.second.startingAyaOrder,
                            )
                        )
                        delay(300)
                        emit(SurahResult.LastStable)
                    }
                },
                {
                    //after the first data load we don't need to care about startingAyaPosition
                    drop(1).map { it.first }
                }
            )
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
            }
            .flatMapLatest { ayas ->
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
            }
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
        ofType<SurahIntent.SaveState>()
            .flatMapLatest {
                saveSurahStateUseCase.saveState(surahStateMapper.map(it.state)).map {isSuccess->
                    SurahResult.SaveSurahState(isSuccess)
                }
            }
    }
}
