package com.islamversity.domain.repo

import com.islamversity.core.Logger
import com.islamversity.core.Mapper
import com.islamversity.db.datasource.CalligraphyLocalDataSource
import com.islamversity.db.datasource.SettingsDataSource
import com.islamversity.db.model.CalligraphyId
import com.islamversity.domain.model.Calligraphy
import com.islamversity.domain.model.QuranReadFontSize
import com.islamversity.domain.model.SettingsCalligraphy
import com.islamversity.domain.model.TranslateReadFontSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.CoroutineContext
import com.islamversity.db.Calligraphy as CalligraphyEntity

interface SettingRepo {

    fun getSecondarySurahNameCalligraphy(context: CoroutineContext = Dispatchers.Default): Flow<Calligraphy>

    fun getFirstAyaTranslationCalligraphy(context: CoroutineContext = Dispatchers.Default): Flow<SettingsCalligraphy>
    fun getSecondAyaTranslationCalligraphy(context: CoroutineContext = Dispatchers.Default): Flow<SettingsCalligraphy>

    fun getAyaMainFontSize(context: CoroutineContext = Dispatchers.Default): Flow<QuranReadFontSize>
    fun getAyaTranslateFontSize(context: CoroutineContext = Dispatchers.Default): Flow<TranslateReadFontSize>

    suspend fun setSecondarySurahNameCalligraphy(
        calligraphy: Calligraphy,
        context: CoroutineContext = Dispatchers.Default
    )

    suspend fun setFirstAyaTranslationCalligraphy(
        calligraphy: SettingsCalligraphy,
        context: CoroutineContext = Dispatchers.Default
    )

    suspend fun setSecondAyaTranslationCalligraphy(
        calligraphy: SettingsCalligraphy,
        context: CoroutineContext = Dispatchers.Default
    )

    suspend fun setAyaMainFontSize(font: QuranReadFontSize, context: CoroutineContext = Dispatchers.Default)
    suspend fun setAyaTranslateFontSize(font: TranslateReadFontSize, context: CoroutineContext = Dispatchers.Default)

    suspend fun setAyaToolbarVisibility(visible : Boolean, context: CoroutineContext = Dispatchers.Default)
    fun getAyaToolbarVisibility(context: CoroutineContext = Dispatchers.Default) : Flow<Boolean>
}

private const val KEY_CALLIGRAPHY_SURAH_NAME_SECONDARY = "KEY_CALLIGRAPHY_SURAH_NAME_SECONDARY"
private const val KEY_CALLIGRAPHY_AYA_TRANSLATION_FIRST = "KEY_CALLIGRAPHY_AYA_TRANSLATION_FIRST"
private const val KEY_CALLIGRAPHY_AYA_TRANSLATION_SECOND = "KEY_CALLIGRAPHY_AYA_TRANSLATION_SECOND"

private const val KEY_FONT_SIZE_AYA_MAIN = "KEY_FONT_SIZE_AYA_MAIN"
private const val KEY_FONT_SIZE_AYA_TRANSLATION = "KEY_FONT_SIZE_AYA_TRANSLATION"

private const val KEY_AYA_TOOLBAR_VISIBLE = "KEY_AYA_TOOLBAR_VISIBLE"

class SettingRepoImpl(
    private val settingsDataSource: SettingsDataSource,
    private val calligraphyDS: CalligraphyLocalDataSource,
    private val mapper: Mapper<CalligraphyEntity, Calligraphy>,
) : SettingRepo {

    override fun getSecondarySurahNameCalligraphy(context: CoroutineContext): Flow<Calligraphy> =
        settingsDataSource.observeKey(
            KEY_CALLIGRAPHY_SURAH_NAME_SECONDARY,
            {
                calligraphyDS.getArabicSurahCalligraphy().first().id.id
            },
            context
        )
            .flatMapMerge {
                Logger.log {
                    "GetSurah : getSecondarySurahNameCalligraphy : id = "  + it.toString()
                }
                calligraphyDS.getCalligraphyById(CalligraphyId(it))
            }
            .map {
                Logger.log {
                    "GetSurah :  getSecondarySurahNameCalligraphy : calligraphy = "  + it.toString()
                }
                mapper.map(it!!)
            }

    override fun getFirstAyaTranslationCalligraphy(context: CoroutineContext): Flow<SettingsCalligraphy> =
        settingsDataSource.observeKey(
            KEY_CALLIGRAPHY_AYA_TRANSLATION_FIRST,
            context
        )
            .flatMapMerge {
                if (it != null) {
                    calligraphyDS.getCalligraphyById(CalligraphyId(it))
                } else {
                    flowOf(null)
                }
            }
            .map {
                if (it != null) {
                    SettingsCalligraphy.Selected(
                        mapper.map(it)
                    )
                } else {
                    SettingsCalligraphy.None
                }
            }

    override fun getSecondAyaTranslationCalligraphy(context: CoroutineContext): Flow<SettingsCalligraphy> =
        settingsDataSource.observeKey(
            KEY_CALLIGRAPHY_AYA_TRANSLATION_SECOND,
            context
        )
            .flatMapMerge {
                if (it != null) {
                    calligraphyDS.getCalligraphyById(CalligraphyId(it))
                } else {
                    flowOf(null)
                }
            }
            .map {
                if (it != null) {
                    SettingsCalligraphy.Selected(
                        mapper.map(it)
                    )
                } else {
                    SettingsCalligraphy.None
                }
            }

    override fun getAyaMainFontSize(context: CoroutineContext): Flow<QuranReadFontSize> =
        settingsDataSource.observeKey(
            KEY_FONT_SIZE_AYA_MAIN,
            {
                QuranReadFontSize.DEFAULT.size.toString()
            },
            context
        )
            .map {
                QuranReadFontSize(it.toInt())
            }
            .onEach {
                Logger.log { "SettingRepo found quran fontSize=$it" }
            }

    override fun getAyaTranslateFontSize(context: CoroutineContext): Flow<TranslateReadFontSize> =
        settingsDataSource.observeKey(
            KEY_FONT_SIZE_AYA_TRANSLATION,
            {
                TranslateReadFontSize.DEFAULT.size.toString()
            },
            context
        )
            .map {
                TranslateReadFontSize(it.toInt())
            }
            .onEach {
                Logger.log { "SettingRepo found translate fontSize=$it" }
            }


    override suspend fun setSecondarySurahNameCalligraphy(calligraphy: Calligraphy, context: CoroutineContext) {
        settingsDataSource.put(KEY_CALLIGRAPHY_SURAH_NAME_SECONDARY, calligraphy.id.id, context)
    }

    override suspend fun setFirstAyaTranslationCalligraphy(
        calligraphy: SettingsCalligraphy,
        context: CoroutineContext
    ) {
        if (calligraphy is SettingsCalligraphy.Selected)
            settingsDataSource.put(KEY_CALLIGRAPHY_AYA_TRANSLATION_FIRST, calligraphy.cal.id.id, context)
        else
            settingsDataSource.put(KEY_CALLIGRAPHY_AYA_TRANSLATION_FIRST, null, context)
    }

    override suspend fun setSecondAyaTranslationCalligraphy(
        calligraphy: SettingsCalligraphy,
        context: CoroutineContext
    ) {
        if (calligraphy is SettingsCalligraphy.Selected)
            settingsDataSource.put(KEY_CALLIGRAPHY_AYA_TRANSLATION_SECOND, calligraphy.cal.id.id, context)
        else
            settingsDataSource.put(KEY_CALLIGRAPHY_AYA_TRANSLATION_SECOND, null, context)
    }

    override suspend fun setAyaMainFontSize(font: QuranReadFontSize, context: CoroutineContext) {
        settingsDataSource.put(KEY_FONT_SIZE_AYA_MAIN, font.size.toString(), context)
    }

    override suspend fun setAyaTranslateFontSize(font: TranslateReadFontSize, context: CoroutineContext) {
        settingsDataSource.put(KEY_FONT_SIZE_AYA_TRANSLATION, font.size.toString(), context)
    }

    override suspend fun setAyaToolbarVisibility(visible: Boolean, context: CoroutineContext) {
        settingsDataSource.put(KEY_AYA_TOOLBAR_VISIBLE, visible.toString(), context)
    }

    override fun getAyaToolbarVisibility(context: CoroutineContext): Flow<Boolean> =
        settingsDataSource.observeKey(KEY_AYA_TOOLBAR_VISIBLE, false.toString(), context)
                .map {
                    it.toBoolean()
                }

}