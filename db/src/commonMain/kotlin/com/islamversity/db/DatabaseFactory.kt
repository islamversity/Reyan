package com.islamversity.db

import com.islamversity.db.model.*
import com.squareup.sqldelight.db.SqlDriver

fun createMainDB(
    driver: SqlDriver
): Main =
    Main(
        driver,
        provideAyaAdapter(),
        provideAyaContentAdapter(),
        provideCalligraphyAdapter(),
        provideNameAdapter(),
        provideSajdahAdapter(),
        provideSurahRevealTypeAdapter(),
        provideSurahAdapter()
    )


private val ayaIdAdapter = AyaIdAdapter()
private val ayaOrderIdAdapter = AyaOrderIdAdapter()

private val surahIdAdapter = SurahIdAdapter()
private val surahOrderIdAdapter = SurahOrderIdAdapter()

private val ayaContentIdAdapter = AyaContentIdAdapter()

private val calligraphyIdAdapter = CalligraphyIdAdapter()
private val languageCodeAdapter = LanguageCodeAdapter()
private val calligraphyNameAdapter = CalligraphyNameAdapter()
private val calligraphyAdapter = CalligraphyAdapter()

private val sajdahIdAdapter = SajdaIdAdapter()
private val sajdahTypeFlagAdapter = SajdahTypeFlagAdapter()

private val nameIdAdapter = NameIdAdapter()
private val rawIdAdapter = RawIdAdapter()

private val surahRevealTypeIdAdapter = SurahRevealTypeIdAdapter()
private val surahFlagAdapter = RevealTypeFlagAdapter()

private fun provideAyaAdapter() =
    Aya.Adapter(ayaIdAdapter, ayaOrderIdAdapter, surahIdAdapter, sajdahIdAdapter, sajdahTypeFlagAdapter)

private fun provideAyaContentAdapter(): Aya_content.Adapter =
    Aya_content.Adapter(ayaContentIdAdapter, ayaIdAdapter, calligraphyIdAdapter)

private fun provideCalligraphyAdapter(): Calligraphy.Adapter =
    Calligraphy.Adapter(calligraphyIdAdapter, languageCodeAdapter, calligraphyNameAdapter, calligraphyAdapter)

private fun provideSajdahAdapter(): Sajdah.Adapter =
    Sajdah.Adapter(sajdahIdAdapter, sajdahTypeFlagAdapter)

private fun provideNameAdapter(): Name.Adapter =
    Name.Adapter(nameIdAdapter, rawIdAdapter, calligraphyIdAdapter)

private fun provideSurahRevealTypeAdapter(): SurahRevealType.Adapter =
    SurahRevealType.Adapter(surahRevealTypeIdAdapter, surahFlagAdapter)

private fun provideSurahAdapter(): Surah.Adapter =
    Surah.Adapter(surahIdAdapter, surahOrderIdAdapter, surahRevealTypeIdAdapter, surahFlagAdapter)

