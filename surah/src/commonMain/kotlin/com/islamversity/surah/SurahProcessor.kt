package com.islamversity.surah

import com.islamversity.core.*
import com.islamversity.core.mvi.BaseProcessor
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
import kotlinx.coroutines.flow.*

class SurahProcessor(
    private val navigator: Navigator,
    private val getAyaUseCase: GetAyaUseCase,
    private val ayaMapper: Mapper<AyaRepoModel, AyaUIModel>,
    private val surahRepoHeaderMapper: Mapper<SurahRepoModel, SurahHeaderUIModel>,
    private val settingRepo: SettingRepo,
    private val surahUsecase: GetSurahUsecase,
) : BaseProcessor<SurahIntent, SurahResult>() {

    override fun transformers(): List<FlowBlock<SurahIntent, SurahResult>> = listOf(
        processInitialFetch
    )

    private val processInitialFetch: FlowBlock<SurahIntent, SurahResult> = {
        ofType<SurahIntent.Initial>()
            .flatMapMerge {
                val id = SurahID(it.surahId)
                combine(
                    getSurahDetail(id),
                    getSurahAyas(id),
                ) { surah, ayas ->
                    ayas.toMutableList<UIItem>().apply {
                        add(0, surah)
                    }
                }
            }
            .map {
                SurahResult.Items(it)
            }
    }

    private fun getSurahDetail(id : SurahID): Flow<SurahHeaderUIModel> =
        surahUsecase.getSurah(id)
            .map { repoModel ->
                repoModel ?: error("this screen can not be opened with wrong surahId= $id")
            }
            .mapWith(surahRepoHeaderMapper)
            //we need to get app fontSize and add it to the header

    private fun getSurahAyas(id : SurahID): Flow<List<AyaUIModel>> =
        getAyaUseCase.observeAyaMain(id)
            .mapListWith(ayaMapper)
            .combine(settingRepo.getQuranFontSize().map { it.size }) { uiModel, font ->
                uiModel.map {
                    it.copy(fontSize = font)
                }
            }
}
