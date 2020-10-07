package com.islamversity.surah.settings

import com.islamversity.core.FlowBlock
import com.islamversity.core.Mapper
import com.islamversity.core.listMap
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.core.ofType
import com.islamversity.domain.model.*
import com.islamversity.domain.repo.CalligraphyRepo
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.surah.model.CalligraphyUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

class SurahSettingsProcessor(
    settingsRepo: SettingRepo,
    calligraphyRepo: CalligraphyRepo,
    uiMapper: Mapper<Calligraphy, CalligraphyUIModel>
) : BaseProcessor<SurahSettingsIntent, SurahSettingsResult>() {

    override fun transformers(): List<FlowBlock<SurahSettingsIntent, SurahSettingsResult>> = listOf(
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

    private val getSurahNameCalligraphies: FlowBlock<SurahSettingsIntent, SurahSettingsResult> = {
        ofType<SurahSettingsIntent.Initial>()
            .flatMapMerge {
                calligraphyRepo.getAllSurahNameCalligraphies()
            }
            .map {
                SurahSettingsResult.SurahNameCalligraphies(
                    it.map {
                        uiMapper.map(it)
                    }
                )
            }
    }

    private val getAllAyaCalligraphies: FlowBlock<SurahSettingsIntent, SurahSettingsResult> = {
        ofType<SurahSettingsIntent.Initial>()
            .flatMapMerge {
                calligraphyRepo.getAllAyaCalligraphies()
            }
            .map {
                SurahSettingsResult.AyaCalligraphies(
                    uiMapper.listMap(it)
                )
            }
    }

    private val getQuranFontSize: FlowBlock<SurahSettingsIntent, SurahSettingsResult> = {
        ofType<SurahSettingsIntent.Initial>()
            .flatMapMerge {
                settingsRepo.getAyaMainFontSize()
            }
            .map {
                SurahSettingsResult.QuranFontSize(it.size)
            }
    }


    private val getTranslateFontSize: FlowBlock<SurahSettingsIntent, SurahSettingsResult> = {
        ofType<SurahSettingsIntent.Initial>()
            .flatMapMerge {
                settingsRepo.getAyaTranslateFontSize()
            }
            .map {
                SurahSettingsResult.TranslateFontSize(it.size)
            }
    }

    private val getCurrentSurahNameCalligraphy: FlowBlock<SurahSettingsIntent, SurahSettingsResult> = {
        ofType<SurahSettingsIntent.Initial>()
            .flatMapMerge {
                settingsRepo.getSecondarySurahNameCalligraphy()
            }
            .map {
                SurahSettingsResult.SurahCalligraphy(
                    uiMapper.map(it)
                )
            }
    }

    private val getCurrentAyaCalligraphy: FlowBlock<SurahSettingsIntent, SurahSettingsResult> = {
        ofType<SurahSettingsIntent.Initial>()
            .flatMapMerge {
                settingsRepo.getFirstAyaTranslationCalligraphy()
            }
            .map {
                SurahSettingsResult.FirstAyaTranslationCalligraphy(
                    if (it is SettingsCalligraphy.Selected) {
                        uiMapper.map(it.cal)
                    } else {
                        null
                    }
                )
            }
    }

    private val changeAyaCalligraphy: Flow<SurahSettingsIntent>.() -> Flow<SurahSettingsResult> = {
        ofType<SurahSettingsIntent.NewAyaCalligraphy>()
            .flatMapMerge {
                calligraphyRepo.getCalligraphy(CalligraphyId(it.language.id))
                    .map {
                        it!!
                    }
            }
            .transform {
                settingsRepo.setFirstAyaTranslationCalligraphy(it)
                emit(SurahSettingsResult.FirstAyaTranslationCalligraphy(uiMapper.map(it)))
            }
    }

    private val changeSurahNameCalligraphy: Flow<SurahSettingsIntent>.() -> Flow<SurahSettingsResult> = {
        ofType<SurahSettingsIntent.NewSurahNameCalligraphySelected>()
            .flatMapMerge {
                calligraphyRepo.getCalligraphy(CalligraphyId(it.calligraphy.id))
                    .map {
                        it!!
                    }
            }
            .transform {
                settingsRepo.setSecondarySurahNameCalligraphy(it)
                emit(SurahSettingsResult.SurahCalligraphy(uiMapper.map(it)))
            }
    }

    private val setQuranFontSize: Flow<SurahSettingsIntent>.() -> Flow<SurahSettingsResult> = {
        ofType<SurahSettingsIntent.ChangeQuranFontSize>()
            .transform {
                settingsRepo.setAyaMainFontSize(QuranReadFontSize(it.size))
                emit(SurahSettingsResult.QuranFontSize(it.size))
            }
    }

    private val setTranslateFontSize: Flow<SurahSettingsIntent>.() -> Flow<SurahSettingsResult> = {
        ofType<SurahSettingsIntent.ChangeTranslateFontSize>()
            .transform {
                settingsRepo.setAyaTranslateFontSize(TranslateReadFontSize(it.size))
                emit(SurahSettingsResult.TranslateFontSize(it.size))
            }
    }
}