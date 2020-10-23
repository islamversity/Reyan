package com.islamversity.surah.settings

import com.islamversity.core.FlowBlock
import com.islamversity.core.Mapper
import com.islamversity.core.listMap
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.core.ofType
import com.islamversity.domain.model.*
import com.islamversity.domain.repo.CalligraphyRepo
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.surah.SurahIntent
import com.islamversity.surah.SurahResult
import com.islamversity.surah.model.CalligraphyUIModel
import kotlinx.coroutines.flow.*

class SurahSettingsProcessor(
    settingsRepo: SettingRepo,
    calligraphyRepo: CalligraphyRepo,
    uiMapper: Mapper<Calligraphy, CalligraphyUIModel>
) : BaseProcessor<SurahIntent, SurahResult>() {


    override fun transformers(): List<FlowBlock<SurahIntent, SurahResult>> = listOf(
        getAllTranslationCalligraphies,

        getMainAyaFontSize,
        getTranslateFontSize,
        setMainAyaFontSize,
        setTranslateFontSize,

        getFirstTranslationCalligraphy,
        changeFirstTranslationCalligraphy,

        getSecondTranslationCalligraphy,
        changeSecondTranslationCalligraphy,

    )

    private val getAllTranslationCalligraphies: FlowBlock<SurahIntent, SurahResult> = {
        ofType<SurahIntent.Initial>()
            .flatMapLatest {
                calligraphyRepo.getAllAyaCalligraphies()
            }
            .map {
                SurahResult.Settings.TranslationCalligraphies(
                    uiMapper.listMap(it)
                )
            }
    }

    private val getMainAyaFontSize: FlowBlock<SurahIntent, SurahResult> = {
        ofType<SurahIntent.Initial>()
            .flatMapLatest {
                settingsRepo.getAyaMainFontSize()
            }
            .map {
                SurahResult.Settings.QuranFontSize(it.size)
            }
    }

    private val getTranslateFontSize: FlowBlock<SurahIntent, SurahResult> = {
        ofType<SurahIntent.Initial>()
            .flatMapLatest {
                settingsRepo.getAyaTranslateFontSize()
            }
            .map {
                SurahResult.Settings.TranslateFontSize(it.size)
            }
    }

    private val setMainAyaFontSize: Flow<SurahIntent>.() -> Flow<SurahResult> = {
        ofType<SurahIntent.ChangeSettings.QuranFontSize>()
            .transform {
                settingsRepo.setAyaMainFontSize(QuranReadFontSize(it.size))
                emit(SurahResult.Settings.QuranFontSize(it.size))
            }
    }

    private val setTranslateFontSize: Flow<SurahIntent>.() -> Flow<SurahResult> = {
        ofType<SurahIntent.ChangeSettings.TranslateFontSize>()
            .transform {
                settingsRepo.setAyaTranslateFontSize(TranslateReadFontSize(it.size))
                emit(SurahResult.Settings.TranslateFontSize(it.size))
            }
    }

    private val getFirstTranslationCalligraphy: FlowBlock<SurahIntent, SurahResult> = {
        ofType<SurahIntent.Initial>()
            .flatMapLatest {
                settingsRepo.getFirstAyaTranslationCalligraphy()
            }
            .map {
                SurahResult.Settings.FirstAyaTranslationCalligraphy(
                    if (it is SettingsCalligraphy.Selected) {
                        uiMapper.map(it.cal)
                    } else {
                        null
                    }
                )
            }
    }

    private val changeFirstTranslationCalligraphy: Flow<SurahIntent>.() -> Flow<SurahResult> = {
        ofType<SurahIntent.ChangeSettings.NewFirstTranslation>()
            .flatMapLatest {
                calligraphyRepo.getCalligraphy(CalligraphyId(it.language.id))
                    .map {
                        it!!
                    }
            }
            .transform {
                settingsRepo.setFirstAyaTranslationCalligraphy(it)
                emit(SurahResult.Settings.FirstAyaTranslationCalligraphy(uiMapper.map(it)))
            }
    }

    private val getSecondTranslationCalligraphy: FlowBlock<SurahIntent, SurahResult> = {
        ofType<SurahIntent.Initial>()
            .flatMapLatest {
                settingsRepo.getSecondAyaTranslationCalligraphy()
            }
            .map {
                SurahResult.Settings.SecondAyaTranslationCalligraphy(
                    if (it is SettingsCalligraphy.Selected) {
                        uiMapper.map(it.cal)
                    } else {
                        null
                    }
                )
            }
    }


    private val changeSecondTranslationCalligraphy: Flow<SurahIntent>.() -> Flow<SurahResult> = {
        ofType<SurahIntent.ChangeSettings.NewSecondTranslation>()
            .flatMapLatest {
                calligraphyRepo.getCalligraphy(CalligraphyId(it.language.id))
                    .map {
                        it!!
                    }
            }
            .transform {
                settingsRepo.setSecondAyaTranslationCalligraphy(it)
                emit(SurahResult.Settings.SecondAyaTranslationCalligraphy(uiMapper.map(it)))
            }
    }
}