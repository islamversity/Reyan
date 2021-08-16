package com.islamversity.settings.settings

import com.islamversity.core.*
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.domain.model.*
import com.islamversity.domain.repo.CalligraphyRepo
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.settings.models.CalligraphyUIModel
import kotlinx.coroutines.flow.*
import com.islamversity.navigation.Screens
import com.islamversity.navigation.Navigator
import com.islamversity.navigation.navigateTo



class SettingsProcessor(
    settingsRepo: SettingRepo,
    calligraphyRepo: CalligraphyRepo,
    uiMapper: Mapper<Calligraphy, CalligraphyUIModel>,
    navigator: Navigator
) : BaseProcessor<SettingsIntent, SettingsResult>() {

    override fun transformers(): List<FlowBlock<SettingsIntent, SettingsResult>> = listOf(
        getSurahNameCalligraphies,
        getAllFirstTranslationCalligraphies,
        getAllSecondTranslationCalligraphies,
        getQuranFontSize,
        getTranslateFontSize,

        getCurrentSurahNameCalligraphy,
        getSecondTranslationCalligraphy,
        getFirstTranslationCalligraphy,

        changeSurahNameCalligraphy,
        changeFirstTranslationCalligraphy,
        changeSecondTranslationCalligraphy,
        setQuranFontSize,
        setTranslateFontSize,
        licensesClicked
    )

    private val getSurahNameCalligraphies: FlowBlock<SettingsIntent, SettingsResult> = {
        ofType<SettingsIntent.Initial>()
            .flatMapLatest {
                calligraphyRepo.getAllSurahNameCalligraphies()
            }
            .map {
                SettingsResult.SecondSurahNameCalligraphies(
                    it.map {
                        uiMapper.map(it)
                    }
                )
            }
    }

    private val getAllFirstTranslationCalligraphies: FlowBlock<SettingsIntent, SettingsResult> = {
        ofType<SettingsIntent.Initial>()
            .flatMapLatest {
                calligraphyRepo.getAllAyaCalligraphies()
            }
            .map {
                val noneOption = it.toMutableList()
                noneOption.add(0, Calligraphy.EMPTY)

                SettingsResult.FirstTranslationCalligraphies(
                    uiMapper.listMap(noneOption)
                )
            }
    }
    private val getAllSecondTranslationCalligraphies: FlowBlock<SettingsIntent, SettingsResult> = {
        ofType<SettingsIntent.Initial>()
            .flatMapLatest {
                calligraphyRepo.getAllAyaCalligraphies()
            }
            .map {
                val noneOption = it.toMutableList()
                noneOption.add(0, Calligraphy.EMPTY)

                SettingsResult.SecondTranslationCalligraphies(
                    uiMapper.listMap(noneOption)
                )
            }
    }

    private val getQuranFontSize: FlowBlock<SettingsIntent, SettingsResult> = {
        ofType<SettingsIntent.Initial>()
            .flatMapLatest {
                settingsRepo.getAyaMainFontSize()
            }
            .map {
                SettingsResult.QuranFontSize(it.size)
            }
    }

    private val getTranslateFontSize: FlowBlock<SettingsIntent, SettingsResult> = {
        ofType<SettingsIntent.Initial>()
            .flatMapLatest {
                settingsRepo.getAyaTranslateFontSize()
            }
            .map {
                SettingsResult.TranslateFontSize(it.size)
            }
    }

    private val getCurrentSurahNameCalligraphy: FlowBlock<SettingsIntent, SettingsResult> = {
        ofType<SettingsIntent.Initial>()
            .flatMapLatest {
                settingsRepo.getSecondarySurahNameCalligraphy()
            }
            .map {
                SettingsResult.SecondSurahCalligraphy(
                    uiMapper.map(it)
                )
            }
    }

    private val getFirstTranslationCalligraphy: FlowBlock<SettingsIntent, SettingsResult> = {
        ofType<SettingsIntent.Initial>()
            .flatMapMerge {
                settingsRepo.getFirstAyaTranslationCalligraphy()
            }
            .map {
                SettingsResult.FirstTranslationCalligraphy(
                    if (it is SettingsCalligraphy.Selected) {
                        uiMapper.map(it.cal)
                    } else {
                        null
                    }
                )
            }
    }

    private val getSecondTranslationCalligraphy: FlowBlock<SettingsIntent, SettingsResult> = {
        ofType<SettingsIntent.Initial>()
            .flatMapLatest {
                settingsRepo.getSecondAyaTranslationCalligraphy()
            }
            .map {
                SettingsResult.SecondTranslationCalligraphy(
                    if (it is SettingsCalligraphy.Selected) {
                        uiMapper.map(it.cal)
                    } else {
                        null
                    }
                )
            }
    }

    private val changeFirstTranslationCalligraphy: Flow<SettingsIntent>.() -> Flow<SettingsResult> = {
        ofType<SettingsIntent.NewFirstTranslation>()
            .flatMapLatest {
                if (it.language.id == Calligraphy.EMPTY.id.id) {
                    return@flatMapLatest flowOf(SettingsCalligraphy.None)
                }

                calligraphyRepo.getCalligraphy(
                    CalligraphyId(it.language.id)
                ).map {
                    SettingsCalligraphy.Selected(it!!)
                }
            }
            .transform {
                settingsRepo.setFirstAyaTranslationCalligraphy(it)

                if (it is SettingsCalligraphy.Selected) {
                    emit(SettingsResult.FirstTranslationCalligraphy(uiMapper.map(it.cal)))
                } else {
                    emit(SettingsResult.FirstTranslationCalligraphy(uiMapper.map(Calligraphy.EMPTY)))
                }
            }
    }

    private val changeSecondTranslationCalligraphy: Flow<SettingsIntent>.() -> Flow<SettingsResult> =
        {
            ofType<SettingsIntent.NewSecondTranslation>()
                .flatMapLatest {
                    if (it.language.id == Calligraphy.EMPTY.id.id) {
                        return@flatMapLatest flowOf(SettingsCalligraphy.None)
                    }

                    calligraphyRepo.getCalligraphy(
                        CalligraphyId(it.language.id)
                    ).map {
                        SettingsCalligraphy.Selected(it!!)
                    }
                }.transform {
                    settingsRepo.setSecondAyaTranslationCalligraphy(it)

                    if (it is SettingsCalligraphy.Selected) {
                        emit(SettingsResult.SecondTranslationCalligraphy(uiMapper.map(it.cal)))
                    } else {
                        emit(SettingsResult.SecondTranslationCalligraphy(uiMapper.map(Calligraphy.EMPTY)))
                    }
                }
        }

    private val changeSurahNameCalligraphy: Flow<SettingsIntent>.() -> Flow<SettingsResult> = {
        ofType<SettingsIntent.NewSecondSurahNameCalligraphySelected>()
            .flatMapLatest {
                calligraphyRepo.getCalligraphy(CalligraphyId(it.calligraphy.id))
            }
            .transform {
                if (it != null) {
                    settingsRepo.setSecondarySurahNameCalligraphy(it)
                    emit(SettingsResult.SecondSurahCalligraphy(uiMapper.map(it)))
                    Logger.log { it.toString() }
                }
            }
    }

    private val setQuranFontSize: Flow<SettingsIntent>.() -> Flow<SettingsResult> = {
        ofType<SettingsIntent.ChangeQuranFontSize>()
            .transform {
                settingsRepo.setAyaMainFontSize(QuranReadFontSize(it.size))
                emit(SettingsResult.QuranFontSize(it.size))
            }
    }

    private val setTranslateFontSize: Flow<SettingsIntent>.() -> Flow<SettingsResult> = {
        ofType<SettingsIntent.ChangeTranslateFontSize>()
            .transform {
                settingsRepo.setAyaTranslateFontSize(TranslateReadFontSize(it.size))
                emit(SettingsResult.TranslateFontSize(it.size))
            }
    }

    private val licensesClicked: FlowBlock<SettingsIntent, SettingsResult> = {
        ofType<SettingsIntent.LicensesClicked>()
            .map {
                Screens.Licenses()
            }
            .navigateTo(navigator)
    }
}