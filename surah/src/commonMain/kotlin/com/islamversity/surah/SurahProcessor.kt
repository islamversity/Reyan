package com.islamversity.surah

import com.islamversity.core.*
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.domain.model.aya.AyaRepoModel
import com.islamversity.domain.model.surah.SurahID
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.domain.repo.aya.GetAyaUseCase
import com.islamversity.domain.repo.surah.GetSurahUsecase
import com.islamversity.navigation.Navigator
import com.islamversity.surah.model.AyaUIModel
import com.islamversity.surah.model.SurahHeaderUIModel
import com.islamversity.surah.model.UIItem
import com.islamversity.surah.settings.SurahSettingsProcessor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class SurahProcessor(
        private val navigator: Navigator,
        private val getAyaUseCase: GetAyaUseCase,
        private val ayaMapper: Mapper<AyaRepoModel, AyaUIModel>,
        private val surahRepoHeaderMapper: Mapper<SurahRepoModel, SurahHeaderUIModel>,
        private val settingRepo: SettingRepo,
        private val surahUsecase: GetSurahUsecase,
        private val settingsProcessor: MviProcessor<SurahIntent, SurahResult>
) : BaseProcessor<SurahIntent, SurahResult>() {

    override fun transformers(): List<FlowBlock<SurahIntent, SurahResult>> = listOf(
            processInitialFetch,
            mainAyaFontSizeProcessor,
            translationFontSizeProcessor,
            ayaToolbarVisibleProcessor,
            settingsProcessor.actionProcessor,
    )

    private val processInitialFetch: FlowBlock<SurahIntent, SurahResult> = {
        ofType<SurahIntent.Initial>()
                .flatMapLatest { initial ->
                    val id = SurahID(initial.surahId)
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
                                if (it.second.startingAyaPosition == 0L) {
                                    //Screen starts from the top not any number
                                    return@transform
                                }

                                val aya = it.first.items.find { item ->
                                    item is AyaUIModel && item.order == it.second.startingAyaPosition
                                }
                                        ?: error("The surah does not contain aya with order= ${it.second.startingAyaPosition}")

                                emit(
                                        SurahResult.ShowAyaNumber(
                                                position = it.first.items.indexOf(aya),
                                                id = aya.rowId,
                                                orderID = it.second.startingAyaPosition,
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

    private fun getSurahDetail(id: SurahID): Flow<SurahHeaderUIModel> =
            surahUsecase.getSurah(id)
                    .map { repoModel ->
                        repoModel ?: error("this screen can not be opened with wrong surahId= $id")
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
}
