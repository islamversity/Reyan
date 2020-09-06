package com.islamversity.surah

import com.islamversity.core.FlowBlock
import com.islamversity.core.Mapper
import com.islamversity.core.mapListWith
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.core.ofType
import com.islamversity.domain.model.aya.AyaRepoModel
import com.islamversity.domain.model.surah.SurahID
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.domain.repo.aya.GetAyaUseCase
import com.islamversity.navigation.Navigator
import com.islamversity.surah.model.AyaUIModel
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map

class SurahProcessor(
    private val navigator: Navigator,
    private val getAyaUseCase: GetAyaUseCase,
    private val ayaMapper: Mapper<AyaRepoModel, AyaUIModel>,
    private val settingRepo: SettingRepo,
) : BaseProcessor<SurahIntent, SurahResult>() {

    override fun transformers(): List<FlowBlock<SurahIntent, SurahResult>> = listOf(
        getMainAyaProcessor,
    )

    private val getMainAyaProcessor: FlowBlock<SurahIntent, SurahResult> = {
        ofType<SurahIntent.Initial>()
            .flatMapMerge {
                getAyaUseCase.observeAyaMain(SurahID(it.surahId))
                    .mapListWith(ayaMapper)
                    .map { ayas ->
                        val fontSize = settingRepo.getCurrentFontSize()
                        SurahResult.MainAyasLoaded(ayas, it.startingAyaPosition, fontSize.size.toInt())
                    }
            }
    }
}