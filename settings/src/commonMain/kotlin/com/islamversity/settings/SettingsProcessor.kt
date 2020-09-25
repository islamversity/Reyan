package com.islamversity.settings

import com.islamversity.core.FlowBlock
import com.islamversity.core.Mapper
import com.islamversity.core.listMap
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.core.ofType
import com.islamversity.domain.model.Calligraphy
import com.islamversity.domain.model.CalligraphyId
import com.islamversity.domain.model.QuranReadFontSize
import com.islamversity.domain.model.TranslateReadFontSize
import com.islamversity.domain.repo.CalligraphyRepo
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.settings.models.CalligraphyUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

class SettingsProcessor(
    settingsRepo: SettingRepo,
    calligraphyRepo: CalligraphyRepo,
    uiMapper: Mapper<Calligraphy, CalligraphyUIModel>
) : BaseProcessor<SettingsIntent, SettingsResult>() {

    override fun transformers(): List<FlowBlock<SettingsIntent, SettingsResult>> = listOf(
        getSurahNameCalligraphies,
        getAllAyaCalligraphies,

        getQuranFontSize,
        getTranslateFontSize,

        getCurrentSurahNameCalligraphy,
        getCurrentAyaCalligraphy,

        changeSurahNameCalligraphy,
        changeAyaCalligraphy,
        setQuranFontSize,
        setTranslateFontSize
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

    private val getQuranFontSize: FlowBlock<SettingsIntent, SettingsResult> = {
        ofType<SettingsIntent.Initial>()
            .flatMapMerge {
                settingsRepo.getQuranFontSize()
            }
            .map {
                SettingsResult.QuranFontSize(it.size)
            }
    }


    private val getTranslateFontSize: FlowBlock<SettingsIntent, SettingsResult> = {
        ofType<SettingsIntent.Initial>()
            .flatMapMerge {
                settingsRepo.getTranslateFontSize()
            }
            .map {
                SettingsResult.TranslateFontSize(it.size)
            }
    }

    private val getCurrentSurahNameCalligraphy: FlowBlock<SettingsIntent, SettingsResult> = {
        ofType<SettingsIntent.Initial>()
            .flatMapMerge {
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
            .flatMapMerge {
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
                settingsRepo.setQuranReadFont(QuranReadFontSize(it.size))
                emit(SettingsResult.QuranFontSize(it.size))
            }
    }

    private val setTranslateFontSize: Flow<SettingsIntent>.() -> Flow<SettingsResult> = {
        ofType<SettingsIntent.ChangeTranslateFontSize>()
            .transform {
                settingsRepo.setTranslateReadFont(TranslateReadFontSize(it.size))
                emit(SettingsResult.TranslateFontSize(it.size))
            }
    }
}