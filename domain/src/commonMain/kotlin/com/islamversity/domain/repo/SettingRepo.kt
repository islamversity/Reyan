package com.islamversity.domain.repo

import com.islamversity.core.Mapper
import com.islamversity.db.datasource.CalligraphyLocalDataSource
import com.islamversity.db.model.CalligraphyId
import com.islamversity.domain.model.Calligraphy
import com.islamversity.domain.model.QuranReadFontSize
import com.russhwolf.settings.Settings
import com.russhwolf.settings.invoke
import com.russhwolf.settings.set
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlin.coroutines.CoroutineContext
import com.islamversity.db.Calligraphy as CalligraphyEntity

interface SettingRepo {

    suspend fun getCurrentSurahCalligraphy(context: CoroutineContext = Dispatchers.Default): Calligraphy
    suspend fun getCurrentQuranReadCalligraphy(context: CoroutineContext = Dispatchers.Default): Calligraphy
    suspend fun getCurrentFontSize(context: CoroutineContext = Dispatchers.Default): QuranReadFontSize

    fun setSurahCalligraphy(calligraphy: Calligraphy)
    fun setQuranReadCalligraphy(calligraphy: Calligraphy)
    fun setQuranReadFont(font: QuranReadFontSize)
}

private const val KEY_SURAH_CALLIGRAPHY = "KEY_SURAH_CALLIGRAPHY"
private const val KEY_QURAN_READ_CALLIGRAPHY = "KEY_QURAN_READ_CALLIGRAPHY"
private const val KEY_QURAN_READ_FONT_SIZE = "KEY_QURAN_READ_FONT_SIZE"

class SettingRepoImpl(
    private val settings: Settings = Settings(),
    private val calligraphyDS: CalligraphyLocalDataSource,
    private val mapper: Mapper<CalligraphyEntity, Calligraphy>
) : SettingRepo {

    override suspend fun getCurrentSurahCalligraphy(context: CoroutineContext): Calligraphy {
        val calligraphyEntity = settings.getStringOrNull(KEY_SURAH_CALLIGRAPHY)?.let {
            calligraphyDS.getCalligraphyById(CalligraphyId(it)).first()
        } ?: calligraphyDS.getArabicSurahCalligraphy().first()

        return mapper.map(calligraphyEntity)
    }

    override suspend fun getCurrentQuranReadCalligraphy(context: CoroutineContext): Calligraphy {
        val calligraphyEntity = settings.getStringOrNull(KEY_QURAN_READ_CALLIGRAPHY)?.let {
            calligraphyDS.getCalligraphyById(CalligraphyId(it)).first()
        } ?: calligraphyDS.getArabicSimpleAyaCalligraphy().first()

        return mapper.map(calligraphyEntity)
    }

    override suspend fun getCurrentFontSize(context: CoroutineContext): QuranReadFontSize {
        return settings.getDoubleOrNull(KEY_QURAN_READ_FONT_SIZE)?.let {
            QuranReadFontSize(it)
        } ?: QuranReadFontSize.DEFAULT
    }

    override fun setSurahCalligraphy(calligraphy: Calligraphy) {
        settings[KEY_SURAH_CALLIGRAPHY] = calligraphy.id.id
    }

    override fun setQuranReadCalligraphy(calligraphy: Calligraphy) {
        settings[KEY_QURAN_READ_CALLIGRAPHY] = calligraphy.id.id
    }

    override fun setQuranReadFont(font: QuranReadFontSize) {
        settings[KEY_QURAN_READ_FONT_SIZE] = font.size
    }
}