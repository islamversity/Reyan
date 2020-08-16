package com.islamversity.settings

import com.islamversity.core.FlowBlock
import com.islamversity.core.Mapper
import com.islamversity.core.listMap
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.core.ofType
import com.islamversity.domain.model.*
import com.islamversity.domain.repo.CalligraphyRepo
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.settings.models.CalligraphyUIModel
import com.islamversity.settings.models.SettingsViewValues
import kotlinx.coroutines.flow.*

class SettingsProcessor(
    settingsRepo: SettingRepo,
    calligraphyRepo: CalligraphyRepo,
    uiMapper: Mapper<Calligraphy, CalligraphyUIModel>
) : BaseProcessor<SettingsIntent, SettingsResult>() {

    override fun transformers(): List<FlowBlock<SettingsIntent, SettingsResult>> = listOf(
        getSurahNameCalligraphies,
        getAllAyaCalligraphies,

        getSurahFontSize,
        getCurrentSurahNameCalligraphy,
        getCurrentAyaCalligraphy,

        changeSurahNameCalligraphy,
        changeAyaCalligraphy,
        setQuranFontSize
    )

    private val getSurahNameCalligraphies: FlowBlock<SettingsIntent, SettingsResult> = {
        ofType<SettingsIntent.Initial>()
            .flatMapMerge {
                calligraphyRepo.getAllSurahNameCalligraphies()
            }
            .map {
                SettingsResult.SurahNameCalligraphies(
                    it.map {
                        uiMapper.map(it)
                    }
                )
            }
    }

    private val getAllAyaCalligraphies: FlowBlock<SettingsIntent, SettingsResult> = {
        ofType<SettingsIntent.Initial>()
            .flatMapMerge {
                calligraphyRepo.getAllAyaCalligraphies()
            }
            .map {
                SettingsResult.AyaCalligraphies(
                    uiMapper.listMap(it)
                )
            }
    }

    private val getSurahFontSize: FlowBlock<SettingsIntent, SettingsResult> = {
        ofType<SettingsIntent.Initial>()
            .map {
                settingsRepo.getCurrentFontSize()
            }
            .map {
                SettingsResult.AyaFontSize(
                    it.size
                )
            }
    }

    private val getCurrentSurahNameCalligraphy: FlowBlock<SettingsIntent, SettingsResult> = {
        ofType<SettingsIntent.Initial>()
            .map {
                settingsRepo.getCurrentSurahCalligraphy()
            }
            .map {
                SettingsResult.SurahCalligraphy(
                    uiMapper.map(it)
                )
            }
    }

    private val getCurrentAyaCalligraphy: FlowBlock<SettingsIntent, SettingsResult> = {
        ofType<SettingsIntent.Initial>()
            .map {
                settingsRepo.getCurrentQuranReadCalligraphy()
            }
            .map {
                SettingsResult.AyaCalligraphy(
                    uiMapper.map(it)
                )
            }
    }

    private val changeAyaCalligraphy: Flow<SettingsIntent>.() -> Flow<SettingsResult> = {
        ofType<SettingsIntent.NewAyaCalligraphy>()
            .flatMapMerge {
                calligraphyRepo.getCalligraphy(CalligraphyId(it.language.id))
                    .map {
                        it!!
                    }
            }
            .transform {
                settingsRepo.setQuranReadCalligraphy(it)
                emit(SettingsResult.AyaCalligraphy(uiMapper.map(it)))
            }
    }

    private val changeSurahNameCalligraphy: Flow<SettingsIntent>.() -> Flow<SettingsResult> = {
        ofType<SettingsIntent.NewSurahNameCalligraphySelected>()
            .flatMapMerge {
                calligraphyRepo.getCalligraphy(CalligraphyId(it.calligraphy.id))
                    .map {
                        it!!
                    }
            }
            .transform {
                settingsRepo.setSurahCalligraphy(it)
                emit(SettingsResult.SurahCalligraphy(uiMapper.map(it)))
            }
    }

    private val setQuranFontSize: Flow<SettingsIntent>.() -> Flow<SettingsResult> = {
        ofType<SettingsIntent.ChangeQuranFontSize>()
            .transform {
                settingsRepo.setQuranReadFont(QuranReadFontSize(it.double.toDouble()))
                emit(SettingsResult.AyaFontSize(it.double.toDouble()))
            }
    }
}